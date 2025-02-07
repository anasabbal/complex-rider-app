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
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;

    /**
     * Endpoint to create a new user.
     *
     * @param userCommand The command object containing user details.
     * @return The created user entity.
     */
    @PostMapping
    public Mono<ResponseEntity<UserEntity>> createUser(@RequestBody UserCommand userCommand) {
        return userEntityService.createUser(userCommand)
                .map(user -> ResponseEntity.status(HttpStatus.CREATED).body(user))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)));
    }

    /**
     * Endpoint to update a user's enabled status.
     *
     * @param userId The ID of the user to update.
     * @param enabled The new enabled status.
     * @return The updated user entity.
     */
    @PutMapping("/enabled/{userId}")
    public Mono<ResponseEntity<UserEntity>> updateUserEnabledStatus(
            @PathVariable Long userId, @RequestParam Boolean enabled) {
        return userEntityService.updateUserEnabledStatus(userId, enabled)
                .map(user -> ResponseEntity.ok(user))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)));
    }
}
