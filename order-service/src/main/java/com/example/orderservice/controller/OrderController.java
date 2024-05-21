package com.example.orderservice.controller;

import com.example.orderservice.service.CartService;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.domain.Item;
import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.User;
import com.example.orderservice.feignclient.UserClient;
import com.example.orderservice.http.header.HeaderGenerator;
import com.example.orderservice.utilities.OrderUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController // Marks this class as a REST controller
public class OrderController {

    @Autowired
    private UserClient userClient; // Injects the UserClient to fetch user details

    @Autowired
    private OrderService orderService; // Injects the OrderService to handle order-related operations

    @Autowired
    private CartService cartService; // Injects the CartService to handle cart-related operations

    @Autowired
    private HeaderGenerator headerGenerator; // Injects the HeaderGenerator to generate HTTP headers

    // Endpoint to save a new order for a user
    @PostMapping(value = "/order/{userId}")
    public ResponseEntity<Order> saveOrder(
            @PathVariable("userId") Long userId, // The ID of the user placing the order
            @RequestHeader(value = "Cookie") String cartId, // The cart ID from the request header
            HttpServletRequest request) {

        List<Item> cart = cartService.getAllItemsFromCart(cartId); // Retrieves all items from the cart
        User user = userClient.getUserById(userId); // Retrieves the user details using the user ID
        if(cart != null && user != null) {
            Order order = this.createOrder(cart, user); // Creates an order from the cart and user details
            try {
                orderService.saveOrder(order); // Saves the order
                cartService.deleteCart(cartId); // Deletes the cart after saving the order
                // Returns the saved order with success headers and HTTP status 201 CREATED
                return new ResponseEntity<>(
                        order,
                        headerGenerator.getHeadersForSuccessPostMethod(request, order.getId()),
                        HttpStatus.CREATED);
            } catch (Exception ex) {
                ex.printStackTrace();
                // If an error occurs, returns error headers with HTTP status 500 INTERNAL SERVER ERROR
                return new ResponseEntity<>(
                        headerGenerator.getHeadersForError(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        // If the cart or user is null, returns error headers with HTTP status 404 NOT FOUND
        return new ResponseEntity<>(
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND);
    }

    // Private method to create an order from cart items and user details
    private Order createOrder(List<Item> cart, User user) {
        Order order = new Order();
        order.setItems(cart); // Sets the cart items in the order
        order.setUser(user); // Sets the user in the order
        order.setTotal(OrderUtilities.countTotalPrice(cart)); // Calculates and sets the total price of the order
        order.setOrderedDate(LocalDate.now()); // Sets the current date as the order date
        order.setStatus("PAYMENT_EXPECTED"); // Sets the order status
        return order; // Returns the created order
    }
}
