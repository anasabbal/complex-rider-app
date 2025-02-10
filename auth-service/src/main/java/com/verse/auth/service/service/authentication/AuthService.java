package com.verse.auth.service.service.authentication;

import com.verse.auth.service.command.UserCommand;
import com.verse.auth.service.payload.TokenResponse;
import reactor.core.publisher.Mono;

/**
 * The service responsible for handling authentication-related tasks, such as user creation.
 */
public interface AuthService {

    /**
     * Creates a new user from the provided user command.
     *
     * @param userCommand the user data to create a new user.
     * @return a Mono containing the created user entity.
     */
    Mono<String> createUser(UserCommand userCommand);
    Mono<TokenResponse> login(String username, String password);
}
