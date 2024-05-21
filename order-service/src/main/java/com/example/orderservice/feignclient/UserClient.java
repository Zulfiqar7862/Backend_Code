package com.example.orderservice.feignclient;

import com.example.orderservice.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "User", url = "http://localhost:8811/")
// Defines a Feign client to communicate with the user service
public interface UserClient {

    @GetMapping(value = "/users/{id}")
    // Maps to the GET request for retrieving a user by their ID
    public User getUserById(@PathVariable("id") Long id);
    // Method to fetch a user by their ID from the user service
}
