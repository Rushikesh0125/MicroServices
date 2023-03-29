package com.example.orderService.Service;

import com.example.orderService.Model.Order;
import com.example.orderService.Model.OrderLineItems;
import com.example.orderService.Repository.OrderRepository;
import com.example.orderService.dto.InventoryResponse;
import com.example.orderService.dto.OrderLineItemsDto;
import com.example.orderService.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    private Tracer tracer;

    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapTODto)
                .toList();
        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skucodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkucode).toList();


        Span InventoryServiceLookUp = tracer.nextSpan().name("InventoryServiceLookUp");

        try(Tracer.SpanInScope spanInScope = tracer.withSpan(InventoryServiceLookUp.start())){
            InventoryResponse[] result = webClientBuilder.build().get().uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skucodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            assert result != null;
            boolean allProductsInStock = Arrays.stream(result).allMatch(InventoryResponse::isInStock);
            if(allProductsInStock){
                orderRepository.save(order);
                return "Order Placed Successfully";
            }else{
                throw new IllegalArgumentException("product is not in stock");
            }
        }finally {
            InventoryServiceLookUp.end();
        }
    }

    private OrderLineItems mapTODto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setSkucode(orderLineItemsDto.getSkucode());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }
}
