package com.epam.training.thumbnailgenerator.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "thumbnail-config")
@Getter
@Setter
@ToString
public class ThumbnailProperties {
    private int width;
    private int height;
}
