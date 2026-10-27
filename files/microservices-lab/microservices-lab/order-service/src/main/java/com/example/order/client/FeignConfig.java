package com.example.order.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class FeignConfig {

    /**
     * A RequestInterceptor bean applies to every Feign client: here it tags outgoing calls with the caller's name.
     * (Static header on purpose: the circuit breaker runs Feign calls on another thread, so ThreadLocal data
     * such as MDC or the current request would NOT be visible here.)
     */
    @Bean
    RequestInterceptor callerServiceInterceptor(@Value("${spring.application.name}") String applicationName) {
        return template -> template.header("X-Caller-Service", applicationName);
    }
}
