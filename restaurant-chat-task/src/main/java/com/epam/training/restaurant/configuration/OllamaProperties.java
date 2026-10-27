package com.epam.training.restaurant.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ollama")
@Getter @Setter
public class OllamaProperties {
    private String baseUrl;
    private String modelName;
}
