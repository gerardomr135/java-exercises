package com.example.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan.
 * No @EnableDiscoveryClient needed: the Eureka starter on the classpath registers the service automatically.
 */
@SpringBootApplication
@ConfigurationPropertiesScan          // finds @ConfigurationProperties classes such as ProductProperties
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
