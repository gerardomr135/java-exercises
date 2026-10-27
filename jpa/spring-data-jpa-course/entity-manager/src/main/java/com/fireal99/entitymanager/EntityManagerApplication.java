package com.fireal99.entitymanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class EntityManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntityManagerApplication.class, args);
    }

    @Bean
    CommandLineRunner init(MovieDAO movieDAO) {
        return args -> {
            movieDAO.test();
        };
    }
}
