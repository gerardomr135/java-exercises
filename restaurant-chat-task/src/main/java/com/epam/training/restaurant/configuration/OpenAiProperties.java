package com.epam.training.restaurant.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "openai")
@Getter @Setter
public class OpenAiProperties {
    private String apiKey;
    private String baseUrl;
    private String modelName;
}
