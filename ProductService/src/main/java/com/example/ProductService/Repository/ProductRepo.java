package com.example.ProductService.Repository;

import com.example.ProductService.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface ProductRepo extends MongoRepository<Product, String> {
}
