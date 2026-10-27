package com.epam.rd.autotasks.library.config;

import com.epam.rd.autotasks.library.Author;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorConfig {
    @Bean
	public Author author() {
        return new Author("John Doe");
    }
}