package com.verse.auth.service.controller;


import com.verse.auth.service.command.UserCommand;
import com.verse.auth.service.models.UserEntity;
import com.verse.auth.service.service.UserEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;

    /**
     * Endpoint to create a new user.
     *
     * @param userCommand The command object containing user details.
     * @return The created user entity.
     */
    @PostMapping("/register")
    public Mono<ResponseEntity<UserEntity>> createUser(@RequestBody @Valid UserCommand userCommand) {
        log.info("Received user command: {}", userCommand);
        return userEntityService.createUser(userCommand)
                .map(user -> ResponseEntity.status(HttpStatus.CREATED).body(user))
                .onErrorResume(e -> {
                    log.error("Error occurred while creating user", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null));
                });
    }
    /**
     * Endpoint to get all users.
     *
     * @return The list of all users.
     */
    @GetMapping()
    public Flux<UserEntity> getAllUsers() {
        log.info("Fetching all users");
        return userEntityService.getAllUsers()
                .onErrorResume(e -> {
                    log.error("Error occurred while fetching users", e);
                    return Flux.empty();
                });
    }
}