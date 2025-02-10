package com.verse.auth.service.controller;

import com.verse.auth.service.command.LoginCommand;
import com.verse.auth.service.command.UserCommand;
import com.verse.auth.service.payload.TokenResponse;
import com.verse.auth.service.service.authentication.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint to create a new user (register).
     *
     * @param userCommand The command object containing user details.
     * @return The success message for user registration.
     */
    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(@RequestBody UserCommand userCommand) {
        log.info("Received user command: {}", userCommand);
        return authService.createUser(userCommand)
                .map(message -> ResponseEntity.status(HttpStatus.CREATED).body(message)) // Return success message
                .onErrorResume(e -> {
                    log.error("Error occurred while creating user", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating user"));
                });
    }

    /**
     * Endpoint for user login.
     *
     * @param loginCommand The command object containing username and password.
     * @return The token response for successful login.
     */
    @PostMapping("/login")
    public Mono<ResponseEntity<TokenResponse>> login(@RequestBody LoginCommand loginCommand) {
        log.info("Attempting login for username: {}", loginCommand.getEmail());
        return authService.login(loginCommand.getEmail(), loginCommand.getPassword())
                .map(tokenResponse -> ResponseEntity.status(HttpStatus.OK).body(tokenResponse))
                .onErrorResume(e -> {
                    log.error("Error occurred during login", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
                });
    }
}
