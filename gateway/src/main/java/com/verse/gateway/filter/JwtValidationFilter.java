package com.verse.gateway.filter;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.verse.gateway.utils.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtValidationFilter implements GlobalFilter, Ordered {

    private static final String AUTH_REGISTER_PATH = "/auth/register";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        ServerHttpRequest request = exchange.getRequest();

        // Skip JWT validation for the registration endpoint
        if (path.startsWith(AUTH_REGISTER_PATH)) {
            return chain.filter(exchange);
        }

        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new RuntimeException("Missing Authorization Header");
        }

        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization Header");
        }

        token = token.substring(7); // Remove "Bearer " prefix

        try {
            DecodedJWT decodedJWT = JwtUtil.verifyToken(token);
            System.out.println("Token Verified: " + decodedJWT.getSubject());

            // Forward request with user details
            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("X-User-Id", decodedJWT.getSubject()) // Pass user info
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());

        } catch (JWTVerificationException e) {
            throw new RuntimeException("Invalid JWT Token: " + e.getMessage());
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}