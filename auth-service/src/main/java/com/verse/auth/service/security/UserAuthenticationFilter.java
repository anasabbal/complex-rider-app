package com.verse.auth.service.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class UserAuthenticationFilter implements WebFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public UserAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = jwtTokenProvider.resolveToken(exchange.getRequest().getHeaders().getFirst("Authorization"));

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsernameFromToken(token);

            // Create an authentication object
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, jwtTokenProvider.getAuthoritiesFromToken(token));

            // Create a SecurityContext and set the authentication
            SecurityContext securityContext = new SecurityContextImpl(authentication);

            // Add the SecurityContext to the ReactiveSecurityContextHolder
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
        }

        // If no valid token, proceed without authentication
        return chain.filter(exchange);
    }
}