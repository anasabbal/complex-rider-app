package com.verse.auth.service.service.users;

import com.verse.auth.service.models.UserCredentials;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserEntityService {
    /**
     * Finds a user by their ID.
     *
     * @param userId the ID of the user to be found.
     * @return a Mono containing the found user entity, or empty if not found.
     */
    Mono<UserCredentials> findById(String userId);

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to be found.
     * @return a Mono containing the found user entity, or empty if not found.
     */
    Mono<UserCredentials> findByUsername(String username);

    /**
     * Finds a user by their email address.
     *
     * @param email the email of the user to be found.
     * @return a Mono containing the found user entity, or empty if not found.
     */
    Mono<UserCredentials> findByEmail(String email);

    /**
     * Updates the enabled status of a user.
     *
     * @param userId the ID of the user to update.
     * @param enabled the new enabled status of the user (true to enable, false to disable).
     * @return a Mono containing the updated user entity.
     */
    Mono<UserCredentials> updateUserEnabledStatus(String userId, Boolean enabled);

    /**
     * Retrieves all users.
     *
     * @return a Flux emitting all user entities.
     */
    Flux<UserCredentials> getAllUsers();
}
