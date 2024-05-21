package com.example.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// Annotate the class with @SpringBootApplication to enable auto-configuration
@SpringBootApplication
// Annotate the class with @EnableEurekaServer to enable Eureka server
@EnableEurekaServer
public class EurekaServerApplication {
    // Define the main method to run the Spring application
    public static void main(String[] args) {
        // Run the Spring application
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
