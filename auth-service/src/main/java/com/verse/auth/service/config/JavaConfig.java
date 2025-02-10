package com.verse.auth.service.config;


import com.auth0.jwt.algorithms.Algorithm;
import com.verse.auth.service.utility.JwtAlgorithmManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import static com.verse.auth.service.utility.EncryptionUtility.PRIVATE_KEY;
import static com.verse.auth.service.utility.EncryptionUtility.PUBLIC_KEY;

@Configuration
public class JavaConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public ReactiveAuditorAware<String> auditorAware() {
        return () -> Mono.just("rider-app");
    }
    @Bean
    public Algorithm getTokenAlgorithm() {
        var helper = new JwtAlgorithmManager(PUBLIC_KEY, PRIVATE_KEY);
        return helper.getTokenAlgorithm();
    }
}
