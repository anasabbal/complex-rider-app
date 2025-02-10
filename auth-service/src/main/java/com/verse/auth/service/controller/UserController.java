package com.verse.auth.service.controller;

import com.verse.auth.service.models.UserEntity;
import com.verse.auth.service.service.users.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserEntityService userEntityService;

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

    /**
     * Endpoint to get a user by their username.
     *
     * @param username the username to search for.
     * @return the found user.
     */
    @GetMapping("/username/{username}")
    public Mono<UserEntity> findByUsername(@PathVariable String username) {
        log.info("Fetching user by username: {}", username);
        return userEntityService.findByUsername(username)
                .onErrorResume(e -> {
                    log.error("Error occurred while fetching user by username", e);
                    return Mono.empty();
                });
    }

    /**
     * Endpoint to get a user by their email.
     *
     * @param email the email to search for.
     * @return the found user.
     */
    @GetMapping("/email/{email}")
    public Mono<UserEntity> findByEmail(@PathVariable String email) {
        log.info("Fetching user by email: {}", email);
        return userEntityService.findByEmail(email)
                .onErrorResume(e -> {
                    log.error("Error occurred while fetching user by email", e);
                    return Mono.empty();
                });
    }

    /**
     * Endpoint to update the enabled status of a user.
     *
     * @param userId the user ID to update.
     * @param enabled the new enabled status.
     * @return the updated user.
     */
    @PutMapping("/{userId}/enabled/{enabled}")
    public Mono<UserEntity> updateUserEnabledStatus(@PathVariable String userId, @PathVariable Boolean enabled) {
        log.info("Updating user enabled status for userId: {} to {}", userId, enabled);
        return userEntityService.updateUserEnabledStatus(userId, enabled)
                .onErrorResume(e -> {
                    log.error("Error occurred while updating user enabled status", e);
                    return Mono.empty();
                });
    }
}
