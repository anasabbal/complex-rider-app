package com.verse.auth.service.controller;


import com.verse.auth.service.command.UserCommand;
import com.verse.auth.service.models.UserEntity;
import com.verse.auth.service.service.authentication.AuthService;
import com.verse.auth.service.service.users.UserEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint to create a new user.
     *
     * @param userCommand The command object containing user details.
     * @return The created user entity.
     */
    @PostMapping("/register")
    public Mono<ResponseEntity<UserEntity>> register(@RequestBody @Valid UserCommand userCommand) {
        log.info("Received user command: {}", userCommand);
        return authService.createUser(userCommand)
                .map(user -> ResponseEntity.status(HttpStatus.CREATED).body(user))
                .onErrorResume(e -> {
                    log.error("Error occurred while creating user", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
                });
    }

}