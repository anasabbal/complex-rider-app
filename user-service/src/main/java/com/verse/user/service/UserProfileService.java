package com.verse.user.service;

import com.verse.user.command.UserProfileCommand;
import com.verse.user.models.UserProfile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing user profiles.
 */
public interface UserProfileService {

    /**
     * Retrieves all user profiles.
     *
     * @return a {@link Flux} containing all user profiles.
     */
    Flux<UserProfile> getAllUserProfiles();

    /**
     * Retrieves a user profile by its unique identifier.
     *
     * @param id the unique identifier of the user profile.
     * @return a {@link Mono} containing the user profile, or empty if not found.
     */
    Mono<UserProfile> getUserProfileById(String id);

    /**
     * Creates a new user profile.
     *
     * @param command the user profile data to create.
     * @return a {@link Mono} containing the created user profile.
     */
    Mono<UserProfile> createUserProfile(UserProfileCommand command);

    /**
     * Updates an existing user profile.
     *
     * @param id          the unique identifier of the user profile to update.
     * @param userProfile the updated user profile data.
     * @return a {@link Mono} containing the updated user profile, or empty if not found.
     */
    Mono<UserProfile> updateUserProfile(String id, UserProfile userProfile);

    /**
     * Deletes a user profile by its unique identifier.
     *
     * @param id the unique identifier of the user profile to delete.
     * @return a {@link Mono} signaling completion or error.
     */
    Mono<Void> deleteUserProfile(String id);
}
