package com.verse.auth.service.service.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.verse.auth.service.payload.AppUser;
import reactor.core.publisher.Mono;

import java.util.Date;

public interface JwtService {
    Mono<String> generateToken(AppUser request, Date tokenExpirationTime);

    Mono<DecodedJWT> verifyToken(String token);
}
