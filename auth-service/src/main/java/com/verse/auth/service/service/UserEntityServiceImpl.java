package com.verse.auth.service.service;

import com.verse.auth.service.command.UserCommand;
import com.verse.auth.service.exception.BusinessException;
import com.verse.auth.service.exception.ExceptionPayloadFactory;
import com.verse.auth.service.models.UserEntity;
import com.verse.auth.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserEntity> createUser(UserCommand userCommand) {
        return checkIfUsernameExists(userCommand.getUsername())
                .then(checkIfEmailExists(userCommand.getEmail())
                        .then(createAndSaveUser(userCommand)));
    }

    @Override
    public Mono<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get())));
    }

    @Override
    public Mono<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get())));
    }

    @Override
    public Mono<UserEntity> updateUserEnabledStatus(String userId, Boolean enabled) {
        return userRepository.findById(userId)
                .flatMap(user -> {
                    user.setEnabled(enabled);
                    return userRepository.save(user);
                })
                .switchIfEmpty(Mono.error(new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get())));
    }
    @Override
    public Flux<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }


    /**
     * Checks if a user with the given username exists.
     * @param username the username to check.
     * @return a Mono that emits an error if the username exists.
     */
    private Mono<Void> checkIfUsernameExists(String username) {
        return userRepository.findByUsername(username)
                .flatMap(existingUser -> Mono.error(new BusinessException(ExceptionPayloadFactory.USER_NAME_ALREADY_EXIST.get())));
    }

    /**
     * Checks if a user with the given email exists.
     * @param email the email to check.
     * @return a Mono that emits an error if the email exists.
     */
    private Mono<Void> checkIfEmailExists(String email) {
        return userRepository.findByEmail(email)
                .flatMap(existingUser -> Mono.error(new BusinessException(ExceptionPayloadFactory.EMAIL_ALREADY_EXIST.get())));
    }

    /**
     * Creates and saves a new user entity.
     * @param userCommand the user data.
     * @return a Mono that emits the saved UserEntity.
     */
    private Mono<UserEntity> createAndSaveUser(UserCommand userCommand) {
        UserEntity newUser = UserEntity.create(userCommand);
        newUser.setPassword(passwordEncoder.encode(userCommand.getPassword()));
        return userRepository.save(newUser);
    }
}
