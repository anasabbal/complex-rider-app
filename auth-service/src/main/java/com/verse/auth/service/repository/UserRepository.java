package com.verse.auth.service.repository;

import com.verse.auth.service.models.UserCredentials;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<UserCredentials, String> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to be found
     * @return a Mono containing the found {@link UserCredentials} if it exists, or an empty Mono if not
     */
    Mono<UserCredentials> findByUsername(String username);

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user to be found
     * @return a Mono containing the found {@link UserCredentials} if it exists, or an empty Mono if not
     */
    Mono<UserCredentials> findByEmail(String email);
}
