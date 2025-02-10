package com.verse.auth.service.service.authentication;




import com.verse.auth.service.command.UserCommand;
import com.verse.auth.service.exception.BusinessException;
import com.verse.auth.service.exception.ExceptionPayloadFactory;
import com.verse.auth.service.models.UserEntity;
import com.verse.auth.service.repository.UserRepository;
import com.verse.auth.service.service.users.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Mono<UserEntity> createUser(UserCommand userCommand) {
        return checkIfUsernameExists(userCommand.getUsername())
                .then(checkIfEmailExists(userCommand.getEmail())
                        .then(createAndSaveUser(userCommand)));
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
