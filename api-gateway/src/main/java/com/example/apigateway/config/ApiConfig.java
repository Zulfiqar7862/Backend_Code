package com.example.apigateway.config;

import com.example.apigateway.filter.SessionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Bean
    public SessionFilter sessionFilter(){
        // Create a new SessionFilter object
        return new SessionFilter();
    }
}
