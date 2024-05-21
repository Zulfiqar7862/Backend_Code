package com.example.recommendationservice.repository;

import com.example.recommendationservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface extends JpaRepository, which is an implementation of the Repository interface.
 * It is used to interact with the database and perform CRUD operations on the Product entity.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
