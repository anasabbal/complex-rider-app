package com.verse.auth.service.security;


import com.verse.auth.service.service.authentication.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;



@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements WebFilter {

    private final AuthService authService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Check if the request is for login (e.g., POST to /login)
        if (exchange.getRequest().getURI().getPath().equals("/login") &&
                exchange.getRequest().getMethod() == HttpMethod.POST) {

            // Extract credentials (username and password) from the request body
            return exchange.getRequest().getBody()
                    .collectList()
                    .flatMap(body -> {
                        String requestBody = body.stream()
                                .map(byteBuffer -> byteBuffer.toString())
                                .reduce("", String::concat);
                        // Assume the body contains JSON like { "username": "user", "password": "pass" }
                        String username = extractFieldFromRequestBody(requestBody, "email");
                        String password = extractFieldFromRequestBody(requestBody, "password");

                        // Authenticate user and generate JWT token
                        return authService.login(username, password)
                                .flatMap(tokenResponse -> {
                                    // Set the token in the response header
                                    exchange.getResponse().getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + tokenResponse.getAccessToken());
                                    exchange.getResponse().setStatusCode(HttpStatus.OK);
                                    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                                    return chain.filter(exchange);
                                })
                                .onErrorResume(e -> {
                                    // Handle login failure
                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                    return Mono.empty();
                                });
                    });
        }

        // Continue the chain for non-login requests
        return chain.filter(exchange);
    }
    private String extractFieldFromRequestBody(String requestBody, String fieldName) {
        // Simple method to extract the field value from the JSON request body.
        // You can use a library like Jackson to parse JSON if necessary
        int startIndex = requestBody.indexOf("\"" + fieldName + "\"") + fieldName.length() + 3;
        int endIndex = requestBody.indexOf("\"", startIndex);
        return requestBody.substring(startIndex, endIndex);
    }
}
