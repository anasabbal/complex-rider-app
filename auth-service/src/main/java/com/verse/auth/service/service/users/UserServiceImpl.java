package com.verse.auth.service.service.users;

import com.verse.auth.service.exception.BusinessException;
import com.verse.auth.service.exception.ExceptionPayloadFactory;
import com.verse.auth.service.models.UserCredentials;
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
    public Mono<UserCredentials> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get())));
    }

    @Override
    public Mono<UserCredentials> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get())));
    }
    @Override
    public Mono<UserCredentials> findById(String userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get())));
    }

    @Override
    public Mono<UserCredentials> updateUserEnabledStatus(String userId, Boolean enabled) {
        return userRepository.findById(userId)
                .flatMap(user -> {
                    user.setEnabled(enabled);
                    return userRepository.save(user);
                })
                .switchIfEmpty(Mono.error(new BusinessException(ExceptionPayloadFactory.USER_NOT_FOUND.get())));
    }

    @Override
    public Flux<UserCredentials> getAllUsers() {
        return userRepository.findAll();
    }
}

