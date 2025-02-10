package com.verse.auth.service.service;

import com.verse.auth.service.command.UserCommand;
import com.verse.auth.service.models.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserEntityService {

    /**
     * Creates a new user from the provided {@link UserCommand}.
     * This method converts the {@link UserCommand} into a {@link UserEntity} and saves it to the database.
     *
     * @param userCommand the command containing user details to create a new user
     * @return the created {@link UserEntity} wrapped in a Mono
     */
    Mono<UserEntity> createUser(UserCommand userCommand);

    /**
     * Finds a user by their username.
     * This method searches for a user in the database by their username.
     *
     * @param username the username of the user to be found
     * @return a Mono containing the found {@link UserEntity} if it exists, or an empty Mono if not
     */
    Mono<UserEntity> findByUsername(String username);

    /**
     * Finds a user by their email address.
     * This method searches for a user in the database by their email address.
     *
     * @param email the email of the user to be found
     * @return a Mono containing the found {@link UserEntity} if it exists, or an empty Mono if not
     */
    Mono<UserEntity> findByEmail(String email);

    /**
     * Updates the enabled status of a user.
     * This method enables or disables a user by updating the 'enabled' field in the database.
     *
     * @param userId the ID of the user whose enabled status is to be updated
     * @param enabled the new enabled status of the user (true to enable, false to disable)
     * @return the updated {@link UserEntity} wrapped in a Mono
     */
    Mono<UserEntity> updateUserEnabledStatus(String userId, Boolean enabled);

    /**
     * Retrieves all users from the database.
     * @return a Mono that emits a list of all users.
     */
    Flux<UserEntity> getAllUsers();
}
