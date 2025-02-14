package com.verse.user.controller;


import com.verse.user.command.UserProfileCommand;
import com.verse.user.models.UserProfile;
import com.verse.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;



@RestController
@RequiredArgsConstructor
@RequestMapping("/user-profile")
public class UserProfileController {


    private final UserProfileService userProfileService;

    /**
     * Retrieves a user profile by its unique identifier.
     *
     * @param id the unique identifier of the user profile.
     * @return a {@link Mono} containing the user profile, or an empty {@link Mono} if not found.
     */
    @GetMapping("/{id}")
    public Mono<UserProfile> getUserProfileById(@PathVariable String id) {
        return userProfileService.getUserProfileById(id);
    }

    /**
     * Creates a new user profile.
     *
     * @param command the user profile data to create.
     * @return a {@link Mono} containing the created user profile.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserProfile> createUserProfile(@RequestBody UserProfileCommand command) {
        return userProfileService.createUserProfile(command);
    }

    /**
     * Deletes a user profile by its unique identifier.
     *
     * @param id the unique identifier of the user profile to delete.
     * @return a {@link Mono} indicating completion.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteUserProfile(@PathVariable String id) {
        return userProfileService.deleteUserProfile(id);
    }
}
