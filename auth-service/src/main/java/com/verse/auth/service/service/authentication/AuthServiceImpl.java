package com.verse.auth.service.service.authentication;

import com.verse.auth.service.command.UserCommand;
import com.verse.auth.service.exception.BusinessException;
import com.verse.auth.service.exception.ExceptionPayloadFactory;
import com.verse.auth.service.models.UserEntity;
import com.verse.auth.service.payload.AppUser;
import com.verse.auth.service.payload.TokenResponse;
import com.verse.auth.service.repository.UserRepository;
import com.verse.auth.service.service.token.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public Mono<String> createUser(UserCommand userCommand) {
        log.info("Creating user with username: {}", userCommand.getUsername());

        return checkIfUsernameExists(userCommand.getUsername())
                .then(checkIfEmailExists(userCommand.getEmail())
                        .then(createAndSaveUser(userCommand)
                                .doOnSuccess(savedUser -> log.info("User created with username: {}", savedUser.getUsername()))
                                .thenReturn("User successfully created")));
    }

    @Override
    public Mono<TokenResponse> login(String username, String password) {
        log.info("Attempting login for username: {}", username);

        return userRepository.findByEmail(username)
                .switchIfEmpty(Mono.error(new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get())))
                .flatMap(user -> {
                    // Check if password matches
                    if (!passwordEncoder.matches(password, user.getPassword())) {
                        return Mono.error(new BusinessException(ExceptionPayloadFactory.INVALID_PAYLOAD.get()));
                    }

                    // Generate JWT token if login is successful
                    return generateJwtToken(user);
                });
    }

    // Method to generate JWT token after user is created or logged in
    private Mono<TokenResponse> generateJwtToken(UserEntity user) {
        log.info("Generating JWT token for user: {}", user.getUsername());

        AppUser appUser = new AppUser(
                user.getUsername(),
                user.getId(),
                "device-id-placeholder" // Ideally, you would generate and pass the actual device ID here
        );

        Date tokenExpirationTime = new Date(System.currentTimeMillis() + 3600000); // 1 hour expiration
        return jwtService.generateToken(appUser, tokenExpirationTime)
                .map(token -> {
                    long expiresInSecond = tokenExpirationTime.getTime() / 1000 - System.currentTimeMillis() / 1000;
                    log.info("Generated token for user: {}. Expires in: {} seconds", user.getUsername(), expiresInSecond);
                    return TokenResponse.builder()
                            .accessToken(token)
                            .expiresInSecond(expiresInSecond)
                            .build();
                })
                .doOnNext(tokenResponse -> {
                    log.info("JWT Token generated: {}", tokenResponse.getAccessToken());
                });
    }

    // Check if the username already exists
    private Mono<Void> checkIfUsernameExists(String username) {
        log.info("Checking if username {} exists", username);

        return userRepository.findByUsername(username)
                .flatMap(existingUser -> {
                    log.error("Username {} already exists", username);
                    return Mono.error(new BusinessException(ExceptionPayloadFactory.USER_NAME_ALREADY_EXIST.get()));
                });
    }

    // Check if the email already exists
    private Mono<Void> checkIfEmailExists(String email) {
        log.info("Checking if email {} exists", email);

        return userRepository.findByEmail(email)
                .flatMap(existingUser -> {
                    log.error("Email {} already exists", email);
                    return Mono.error(new BusinessException(ExceptionPayloadFactory.EMAIL_ALREADY_EXIST.get()));
                });
    }

    // Create and save the user entity
    private Mono<UserEntity> createAndSaveUser(UserCommand userCommand) {
        log.info("Creating and saving user: {}", userCommand.getUsername());

        UserEntity newUser = UserEntity.create(userCommand);
        newUser.setPassword(passwordEncoder.encode(userCommand.getPassword()));

        return userRepository.save(newUser)
                .doOnSuccess(user -> log.info("User created successfully with ID: {}", user.getId()))
                .doOnError(e -> log.error("Error occurred while creating user: {}", e.getMessage()));
    }
}
