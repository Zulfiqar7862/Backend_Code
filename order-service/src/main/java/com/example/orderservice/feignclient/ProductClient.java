package com.example.orderservice.feignclient;

import com.example.orderservice.domain.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-catalog-service", url = "http://localhost:8810/")
// Defines a Feign client to communicate with the product-catalog-service
public interface ProductClient {

    @GetMapping(value = "/products/{id}")
    // Maps to the GET request for retrieving a product by its ID
    public Product getProductById(@PathVariable(value = "id") Long productId);
    // Method to fetch a product by its ID from the product-catalog-service
}
