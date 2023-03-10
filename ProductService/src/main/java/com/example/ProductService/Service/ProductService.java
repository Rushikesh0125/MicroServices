package com.example.ProductService.Service;

import com.example.ProductService.DataTransferObjects.ProductRequest;
import com.example.ProductService.DataTransferObjects.ProductResponse;
import com.example.ProductService.Model.Product;
import com.example.ProductService.Repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    public void createProduct(@RequestBody ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepo.save(product);
        log.info("product saved with product ID {}", product.getProductId());
    }

    public List<ProductResponse> getAllPrpducts() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(this::convertToProductResponse).toList();
    }

    private ProductResponse convertToProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
