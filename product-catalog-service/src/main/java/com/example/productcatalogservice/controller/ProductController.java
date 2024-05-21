package com.example.productcatalogservice.controller;

import com.example.productcatalogservice.entity.Product;
import com.example.productcatalogservice.http.header.HeaderGenerator;
import com.example.productcatalogservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
// Marks this class as a RESTful controller component
public class ProductController {

    @Autowired
    private ProductService productService; // Autowired ProductService for product-related operations

    @Autowired
    private HeaderGenerator headerGenerator; // Autowired HeaderGenerator for generating HTTP headers

    // Endpoint to get all products
    @GetMapping (value = "/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products =  productService.getAllProduct();
        if(!products.isEmpty()) {
            // Return a response entity with the list of products and success HTTP headers if products exist
            return new ResponseEntity<List<Product>>(
                    products,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK);
        }
        // Return a response entity with error HTTP headers if no products are found
        return new ResponseEntity<List<Product>>(
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND);
    }

    // Endpoint to get all products by category
    @GetMapping(value = "/products", params = "category")
    public ResponseEntity<List<Product>> getAllProductByCategory(@RequestParam ("category") String category){
        List<Product> products = productService.getAllProductByCategory(category);
        if(!products.isEmpty()) {
            // Return a response entity with the list of products and success HTTP headers if products exist
            return new ResponseEntity<List<Product>>(
                    products,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK);
        }
        // Return a response entity with error HTTP headers if no products are found
        return new ResponseEntity<List<Product>>(
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND);
    }

    // Endpoint to get a product by ID
    @GetMapping (value = "/products/{id}")
    public ResponseEntity<Product> getOneProductById(@PathVariable ("id") long id){
        Product product =  productService.getProductById(id);
        if(product != null) {
            // Return a response entity with the product and success HTTP headers if the product is found
            return new ResponseEntity<Product>(
                    product,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK);
        }
        // Return a response entity with error HTTP headers if the product is not found
        return new ResponseEntity<Product>(
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND);
    }

    // Endpoint to get all products by name
    @GetMapping (value = "/products", params = "name")
    public ResponseEntity<List<Product>> getAllProductsByName(@RequestParam ("name") String name){
        List<Product> products =  productService.getAllProductsByName(name);
        if(!products.isEmpty()) {
            // Return a response entity with the list of products and success HTTP headers if products exist
            return new ResponseEntity<List<Product>>(
                    products,
                    headerGenerator.getHeadersForSuccessGetMethod(),
                    HttpStatus.OK);
        }
        // Return a response entity with error HTTP headers if no products are found
        return new ResponseEntity<List<Product>>(
                headerGenerator.getHeadersForError(),
                HttpStatus.NOT_FOUND);
    }
}
