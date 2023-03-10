package com.example.ProductService.DataTransferObjects;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String productId;
    private String name;
    private String description;
    private BigDecimal price;
}
