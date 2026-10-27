package com.epam.training.thumbnailgenerator.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "messaging-config")
@Getter @Setter
@ToString
public class MessagingProperties {
    private String queueName;
    private String exchangeName;
    private String routingKey;
}
