package com.example.orderservice.service;

import com.example.orderservice.domain.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
// Marks this class as a Spring service component and enables transaction management
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository; // Autowired OrderRepository for database operations

    // Method to save an order
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order); // Save the order using JpaRepository's save method
    }
}
