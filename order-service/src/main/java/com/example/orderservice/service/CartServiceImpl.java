package com.example.orderservice.service;

import com.example.orderservice.domain.Item;
import com.example.orderservice.domain.Product;
import com.example.orderservice.feignclient.ProductClient;
import com.example.orderservice.redis.CartRedisRepository;
import com.example.orderservice.utilities.CartUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
// Marks this class as a Spring service component
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductClient productClient; // Autowired Feign client for fetching product details

    @Autowired
    private CartRedisRepository cartRedisRepository; // Autowired Redis repository for cart operations

    // Method to add an item to the cart
    @Override
    public void addItemToCart(String cartId, Long productId, Integer quantity) {
        Product product = productClient.getProductById(productId); // Fetch product details using Feign client
        Item item = new Item(quantity, product, CartUtilities.getSubTotalForItem(product, quantity)); // Create item object
        cartRedisRepository.addItemToCart(cartId, item); // Add item to cart in Redis
    }

    // Method to get the cart contents
    @Override
    public List<Object> getCart(String cartId) {
        return (List<Object>) cartRedisRepository.getCart(cartId, Item.class); // Retrieve cart contents from Redis
    }

    // Method to change the quantity of an item in the cart
    @Override
    public void changeItemQuantity(String cartId, Long productId, Integer quantity) {
        List<Item> cart = (List) cartRedisRepository.getCart(cartId, Item.class); // Get cart items from Redis
        for (Item item : cart) {
            if ((item.getProduct().getId()).equals(productId)) {
                cartRedisRepository.deleteItemFromCart(cartId, item); // Delete old item
                item.setQuantity(quantity); // Update quantity
                item.setSubTotal(CartUtilities.getSubTotalForItem(item.getProduct(), quantity)); // Update subtotal
                cartRedisRepository.addItemToCart(cartId, item); // Add updated item to cart
            }
        }
    }

    // Method to delete an item from the cart
    @Override
    public void deleteItemFromCart(String cartId, Long productId) {
        List<Item> cart = (List) cartRedisRepository.getCart(cartId, Item.class); // Get cart items from Redis
        for (Item item : cart) {
            if ((item.getProduct().getId()).equals(productId)) {
                cartRedisRepository.deleteItemFromCart(cartId, item); // Delete item from cart
            }
        }
    }

    // Method to check if an item exists in the cart
    @Override
    public boolean checkIfItemIsExist(String cartId, Long productId) {
        List<Item> cart = (List) cartRedisRepository.getCart(cartId, Item.class); // Get cart items from Redis
        for (Item item : cart) {
            if ((item.getProduct().getId()).equals(productId)) {
                return true; // Item exists in cart
            }
        }
        return false; // Item not found in cart
    }

    // Method to get all items from the cart
    @Override
    public List<Item> getAllItemsFromCart(String cartId) {
        List<Item> items = (List) cartRedisRepository.getCart(cartId, Item.class); // Get cart items from Redis
        return items;
    }

    // Method to delete the entire cart
    @Override
    public void deleteCart(String cartId) {
        cartRedisRepository.deleteCart(cartId); // Delete cart from Redis
    }
}
