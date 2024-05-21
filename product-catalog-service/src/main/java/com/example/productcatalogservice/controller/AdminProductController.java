package com.example.productcatalogservice.controller;

import com.example.productcatalogservice.service.ProductService;
import com.example.productcatalogservice.entity.Product;
import com.example.productcatalogservice.http.header.HeaderGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

// annotate the entire class with @RestController, indicates this is a RESTful controller
@RestController
// annotate the mapping with @RequestMapping, indicates the base URL for this controller
@RequestMapping("/admin")
public class AdminProductController {

    // annotate the injected field with @Autowired, indicates that this field should be injected by the Spring framework
    @Autowired
    private ProductService productService;

    // annotate the injected field with @Autowired, indicates that this field should be injected by the Spring framework
    @Autowired
    private HeaderGenerator headerGenerator;

    // annotate the method with @PostMapping, indicates that this method should handle HTTP POST requests
    @PostMapping(value = "/products")
    // return the response entity with the product and the headers
    private ResponseEntity<Product> addProduct(@RequestBody Product product, HttpServletRequest request){
    	// check if the product is not null
    	if(product != null) {
    		// try to add the product
    		try {
    			productService.addProduct(product);
    	        // return the response entity with the product and the headers
    	        return new ResponseEntity<Product>(
    	        		product,
    	        		headerGenerator.getHeadersForSuccessPostMethod(request, product.getId()),
    	        		HttpStatus.CREATED);
    		}catch (Exception e) {
				e.printStackTrace();
				// return the response entity with the error and the headers
				return new ResponseEntity<Product>(
						headerGenerator.getHeadersForError(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
    	}
    	// return the response entity with the error and the headers
    	return new ResponseEntity<Product>(
    			headerGenerator.getHeadersForError(),
    			HttpStatus.BAD_REQUEST);
    }

    // annotate the method with @DeleteMapping, indicates that this method should handle HTTP DELETE requests
    @DeleteMapping(value = "/products/{id}")
    // return the response entity with the headers
    private ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id){
    	// get the product by the id
    	Product product = productService.getProductById(id);
    	// check if the product is not null
    	if(product != null) {
    		// try to delete the product
    		try {
    			productService.deleteProduct(id);
    	        // return the response entity with the headers
    	        return new ResponseEntity<Void>(
    	        		headerGenerator.getHeadersForSuccessGetMethod(),
    	        		HttpStatus.OK);
    		}catch (Exception e) {
				e.printStackTrace();
    	        // return the response entity with the error and the headers
    	        return new ResponseEntity<Void>(
    	        		headerGenerator.getHeadersForError(),
    	        		HttpStatus.INTERNAL_SERVER_ERROR);
			}
    	}
    	// return the response entity with the headers
    	return new ResponseEntity<Void>(headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);      
    }
}
