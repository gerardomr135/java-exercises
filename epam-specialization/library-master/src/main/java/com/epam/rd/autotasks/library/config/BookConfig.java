package com.epam.rd.autotasks.library.config;

import com.epam.rd.autotasks.library.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ AuthorConfig.class, PublisherConfig.class })
public class BookConfig {
    // Another way to inject these configs is via @Autowired
    private final AuthorConfig authorConfig;
    private final PublisherConfig publisherConfig;

    BookConfig(AuthorConfig authorConfig, PublisherConfig publisherConfig) {
        this.authorConfig = authorConfig;
        this.publisherConfig = publisherConfig;
    }

    @Bean(name = "ebook")
    public Book eBook() {
        return new Book("The Adventures of Spring (E-book Edition)", authorConfig.author(), publisherConfig.publisher(),
                "E-book");
    }

    @Bean
    public Book printBook() {
        return new Book("The Adventures of Spring (Print Edition)", authorConfig.author(), publisherConfig.publisher(),
                "Print");
    }
}