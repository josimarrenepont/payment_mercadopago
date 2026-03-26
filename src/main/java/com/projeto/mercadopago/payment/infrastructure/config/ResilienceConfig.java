package com.projeto.mercadopago.payment.infrastructure.config;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpServerErrorException;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    @Bean
    public Retry mercadoPagoRetry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .retryExceptions(HttpServerErrorException.class)
                .build();

        RetryRegistry registry = RetryRegistry.of(config);
        return registry.retry("mercadopago");
    }
}
