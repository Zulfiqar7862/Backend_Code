package com.example.orderservice.redis;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Repository
public class CartRedisRepositoryImpl implements CartRedisRepository{

    // Create an object mapper instance
    private ObjectMapper objectMapper = new ObjectMapper();
    // Create a Jedis instance
    private Jedis jedis = new Jedis();

    @Override
    public void addItemToCart(String key, Object item) {
        try {
            // Convert the object to JSON format
            String jsonObject = objectMapper.writeValueAsString(item);
            // Add the JSON object to the set stored in the Jedis instance
            jedis.sadd(key, jsonObject);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Object> getCart(String key, Class type) {
        Collection<Object> cart = new ArrayList<>();
        // Iterate through the set stored in the Jedis instance
        for (String smember : jedis.smembers(key)) {
            try {
                // Read the JSON object stored in the set and add it to the collection
                cart.add(objectMapper.readValue(smember, type));
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cart;
    }

    @Override
    public void deleteItemFromCart(String key, Object item) {
        try {
            // Convert the object to JSON format
            String itemCart = objectMapper.writeValueAsString(item);
            // Remove the JSON object from the set stored in the Jedis instance
            jedis.srem(key, itemCart);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCart(String key) {
        // Remove all JSON objects from the set stored in the Jedis instance
        jedis.del(key);
    }
}
