package com.example.orderservice.repository;

import com.example.orderservice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Marks this interface as a Spring repository component
public interface OrderRepository extends JpaRepository<Order, Long> {
    // This interface extends JpaRepository to inherit basic CRUD operations for Order entities
    // The entity type is Order, and the primary key type is Long
}
