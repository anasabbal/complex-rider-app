package com.verse.auth.service.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.mapping.event.ReactiveAuditingEntityCallback;
import org.springframework.data.auditing.ReactiveIsNewAwareAuditingHandler;
import org.springframework.beans.factory.ObjectFactory;
import reactor.core.publisher.Mono;

@Configuration
@EnableR2dbcAuditing
public class R2dbcConfig {

    @Bean
    public ReactiveAuditorAware<String> auditorAware() {
        return () -> Mono.just("rider-sys");
    }

    @Bean
    public ReactiveAuditingEntityCallback reactiveAuditingEntityCallback(
            ObjectFactory<ReactiveIsNewAwareAuditingHandler> auditingHandlerFactory) {
        return new ReactiveAuditingEntityCallback(auditingHandlerFactory);
    }
}

