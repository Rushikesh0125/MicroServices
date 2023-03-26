package com.example.orderService.Controller;

import com.example.orderService.Service.OrderService;
import com.example.orderService.dto.OrderRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

    private  final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventory")
    public String createOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "order created successfully";
    }

    public String fallBackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return "OOpsss! something went wrong";
    }
}
