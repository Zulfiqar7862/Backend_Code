package com.example.apigateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// This class extends the WebSecurityConfigurerAdapter and overrides the configure method
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Override the configure method
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Disable CSRF protection and allow all requests to the root path ("/")
		http.csrf().disable().authorizeRequests().antMatchers("/").permitAll();
	}

}

