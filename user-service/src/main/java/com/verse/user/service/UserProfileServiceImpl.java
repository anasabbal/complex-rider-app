package com.verse.user.service;


import com.verse.user.command.UserProfileCommand;
import com.verse.user.exception.UserProfileNotFoundException;
import com.verse.user.models.UserProfile;
import com.verse.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;


    @Override
    public Flux<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll()
                .doOnNext(profile -> log.info("Retrieved user profile: {}", profile))
                .doOnError(error -> log.error("Error retrieving user profiles", error));
    }

    @Override
    public Mono<UserProfile> getUserProfileById(String id) {
        return userProfileRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserProfileNotFoundException("User profile not found for id: " + id)))
                .doOnNext(profile -> log.info("Retrieved user profile with id {}: {}", id, profile))
                .doOnError(error -> log.error("Error retrieving user profile with id {}", id, error));
    }

    @Override
    public Mono<UserProfile> createUserProfile(UserProfileCommand command) {
        return userProfileRepository.save(UserProfile.create(command))
                .doOnSuccess(profile -> log.info("Created user profile: {}", profile))
                .doOnError(error -> log.error("Error creating user profile", error));
    }

    @Override
    public Mono<UserProfile> updateUserProfile(String id, UserProfile userProfile) {
        return null;
    }

    @Override
    public Mono<Void> deleteUserProfile(String id) {
        return userProfileRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserProfileNotFoundException("User profile not found for id: " + id)))
                .flatMap(existingProfile -> userProfileRepository.delete(existingProfile)
                        .doOnSuccess(unused -> log.info("Deleted user profile with id {}", id))
                        .doOnError(error -> log.error("Error deleting user profile with id {}", id, error)));
    }
}