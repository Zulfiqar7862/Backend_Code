package com.example.orderservice.controller;

import com.example.orderservice.service.CartService;
import com.example.orderservice.http.header.HeaderGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@RestController // Marks this class as a REST controller
public class CartController {

	@Autowired
	CartService cartService; // Injects the CartService to handle cart-related operations

	@Autowired
	private HeaderGenerator headerGenerator; // Injects the HeaderGenerator to generate HTTP headers

	// Endpoint to get the cart items
	@GetMapping(value = "/cart")
	public ResponseEntity<List<Object>> getCart(@RequestHeader(value = "Cookie") String cartId){
		List<Object> cart = cartService.getCart(cartId); // Retrieves the cart using the cartId from the request header
		if(!cart.isEmpty()) {
			// If the cart is not empty, return the cart items with success headers and HTTP status 200 OK
			return new ResponseEntity<>(
					cart,
					headerGenerator.getHeadersForSuccessGetMethod(),
					HttpStatus.OK);
		}
		// If the cart is empty, return an error header with HTTP status 404 NOT FOUND
		return new ResponseEntity<>(
				headerGenerator.getHeadersForError(),
				HttpStatus.NOT_FOUND);
	}

	// Endpoint to add an item to the cart
	@PostMapping(value = "/cart", params = {"productId", "quantity"})
	public ResponseEntity<List<Object>> addItemToCart(
			@RequestParam("productId") Long productId, // The ID of the product to be added
			@RequestParam("quantity") Integer quantity, // The quantity of the product
			@RequestHeader(value = "Cookie") String cartId, // The cart ID from the request header
			HttpServletRequest request) {
		List<Object> cart = cartService.getCart(cartId); // Retrieves the cart using the cartId
		if(cart != null) {
			if(cart.isEmpty()){
				cartService.addItemToCart(cartId, productId, quantity); // Adds item if the cart is empty
			} else {
				if(cartService.checkIfItemIsExist(cartId, productId)){
					cartService.changeItemQuantity(cartId, productId, quantity); // Changes item quantity if it exists
				} else {
					cartService.addItemToCart(cartId, productId, quantity); // Adds new item if it doesn't exist
				}
			}
			// Returns the updated cart with success headers and HTTP status 201 CREATED
			return new ResponseEntity<>(
					cart,
					headerGenerator.getHeadersForSuccessPostMethod(request, Long.parseLong(cartId)),
					HttpStatus.CREATED);
		}
		// If the cart is null, return an error header with HTTP status 400 BAD REQUEST
		return new ResponseEntity<>(
				headerGenerator.getHeadersForError(),
				HttpStatus.BAD_REQUEST);
	}

	// Endpoint to remove an item from the cart
	@DeleteMapping(value = "/cart", params = "productId")
	public ResponseEntity<Void> removeItemFromCart(
			@RequestParam("productId") Long productId, // The ID of the product to be removed
			@RequestHeader(value = "Cookie") String cartId){ // The cart ID from the request header
		List<Object> cart = cartService.getCart(cartId); // Retrieves the cart using the cartId
		if(cart != null) {
			cartService.deleteItemFromCart(cartId, productId); // Deletes the item from the cart
			// Returns success headers and HTTP status 200 OK
			return new ResponseEntity<>(
					headerGenerator.getHeadersForSuccessGetMethod(),
					HttpStatus.OK);
		}
		// If the cart is null, return an error header with HTTP status 404 NOT FOUND
		return new ResponseEntity<>(
				headerGenerator.getHeadersForError(),
				HttpStatus.NOT_FOUND);
	}
}
