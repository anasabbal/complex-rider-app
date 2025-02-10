package com.verse.auth.service.service.users;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserEntityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


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
    public Mono<UserEntity> findById(String userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get())));
    }

    @Override
    public Mono<UserEntity> updateUserEnabledStatus(String userId, Boolean enabled) {
        return userRepository.findById(userId)
                .flatMap(user -> {
                    // Update the enabled status
                    user.setEnabled(enabled);

                    // Save the updated user entity
                    return userRepository.save(user);
                })
                .switchIfEmpty(Mono.error(new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get())));
    }

    @Override
    public Flux<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    private Mono<Void> checkIfUsernameExists(String username) {
        return userRepository.findByUsername(username)
                .flatMap(existingUser -> Mono.error(new BusinessException(ExceptionPayloadFactory.USER_NAME_ALREADY_EXIST.get())));
    }

    private Mono<Void> checkIfEmailExists(String email) {
        return userRepository.findByEmail(email)
                .flatMap(existingUser -> Mono.error(new BusinessException(ExceptionPayloadFactory.EMAIL_ALREADY_EXIST.get())));
    }

    private Mono<UserEntity> createAndSaveUser(UserCommand userCommand) {
        UserEntity newUser = UserEntity.create(userCommand);
        newUser.setPassword(passwordEncoder.encode(userCommand.getPassword()));
        return userRepository.save(newUser);
    }
}

