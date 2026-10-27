package com.example.product.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RefreshScope beans are destroyed and rebuilt on POST /actuator/refresh, so @Value fields
 * pick up new values from the Config Server WITHOUT restarting the service.
 */
@RestController
@RefreshScope
@RequestMapping("/api/info")
public class InfoController {

    @Value("${product.banner:no banner configured}")
    private String banner;

    private final Environment environment;

    public InfoController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping
    public Map<String, Object> info() {
        return Map.of(
                "service", environment.getProperty("spring.application.name", "unknown"),
                "port", environment.getProperty("server.port", "unknown"),
                "profiles", environment.getActiveProfiles(),
                "banner", banner);
    }
}
