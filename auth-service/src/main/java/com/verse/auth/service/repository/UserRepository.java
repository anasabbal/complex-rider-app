package com.verse.auth.service.repository;

import com.verse.auth.service.models.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to be found
     * @return a Mono containing the found {@link UserEntity} if it exists, or an empty Mono if not
     */
    Mono<UserEntity> findByUsername(String username);

    /**
     * Finds a user by their email.
     *
     * @param email the email of the user to be found
     * @return a Mono containing the found {@link UserEntity} if it exists, or an empty Mono if not
     */
    Mono<UserEntity> findByEmail(String email);
}
