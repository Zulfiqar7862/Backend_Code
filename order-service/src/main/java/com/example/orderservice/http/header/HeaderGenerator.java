package com.example.orderservice.http.header;

import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
// Marks this class as a Spring service component
public class HeaderGenerator {

	// Method to generate HTTP headers for successful GET requests
	public HttpHeaders getHeadersForSuccessGetMethod() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
		return httpHeaders;
	}

	// Method to generate HTTP headers for error responses
	public HttpHeaders getHeadersForError() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/problem+json; charset=UTF-8");
		return httpHeaders;
	}

	// Method to generate HTTP headers for successful POST requests
	public HttpHeaders getHeadersForSuccessPostMethod(HttpServletRequest request, Long newResourceId) {
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			// Set the location header to the URI of the newly created resource
			httpHeaders.setLocation(new URI(request.getRequestURI() + "/" + newResourceId));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
		return httpHeaders;
	}
}
