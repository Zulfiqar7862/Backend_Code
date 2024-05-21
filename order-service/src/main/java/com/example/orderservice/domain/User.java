package com.example.orderservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity // Marks this class as a JPA entity
@Table(name = "users") // Specifies the table name in the database
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies the primary key generation strategy
    private Long id;

    @Column(name = "user_name") // Maps this field to the 'user_name' column in the database
    @NotNull // Ensures this field is not null
    private String userName;

    @OneToMany(mappedBy = "user") // Defines a one-to-many relationship with the Order entity
    @JsonIgnore // Ignores this field during JSON serialization
    private List<Order> orders;

    // Getter for userName
    public String getUserName() {
        return userName;
    }

    // Setter for userName
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getter for orders
    public List<Order> getOrders() {
        return orders;
    }

    // Setter for orders
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
