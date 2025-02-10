package com.verse.auth.service.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.verse.auth.service.models.UserEntity;
import com.verse.auth.service.service.token.JwtService;
import com.verse.auth.service.service.users.UserEntityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;

import static com.verse.auth.service.utility.JwtUtility.TOKEN_CLAIM_DEVICE;
import static com.verse.auth.service.utility.JwtUtility.TOKEN_CLAIM_USER;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private final JwtService jwtService;
    private final UserEntityService userEntityService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        log.info("AuthenticationManager:: authenticate:: reached {}", System.currentTimeMillis());
        return Mono.just(authentication.getCredentials().toString())
                .flatMap(this::verifyToken)
                .flatMap(this::getAuthentication);
    }

    private Mono<Authentication> getAuthentication(AuthenticationTokenData appUser) {
        return userEntityService.findById(appUser.getUserId()) // Fetch UserEntity by userId
                .flatMap(userEntity -> {
                    // Fetch roles from the user entity and create the authentication token
                    Collection<SimpleGrantedAuthority> authorities = getGrantedAuthorities(userEntity);
                    return Mono.just(
                            new UsernamePasswordAuthenticationToken(userEntity, null, authorities)
                    );
                });
    }

    private Collection<SimpleGrantedAuthority> getGrantedAuthorities(UserEntity userEntity) {
        // Fetch roles from the user entity and assign them as granted authorities
        return userEntity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .toList();
    }

    private Mono<AuthenticationTokenData> verifyToken(String token) {
        return jwtService.verifyToken(token)
                .filter(decodedJWT -> decodedJWT.getAudience() != null && !decodedJWT.getAudience().isEmpty())
                .flatMap(tokenData -> getAppUser(tokenData, token));
    }

    private Mono<AuthenticationTokenData> getAppUser(DecodedJWT decodedJWT, String token) {
        return Mono.just(AuthenticationTokenData.builder()
                .userAudience(decodedJWT.getAudience().get(0))
                .userId(decodedJWT.getClaim(TOKEN_CLAIM_USER).asString())
                .deviceId(decodedJWT.getClaim(TOKEN_CLAIM_DEVICE).asString())
                .token(token)
                .build()
        );
    }
}
