package com.example.product.config;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.product.domain.Product;
import com.example.product.repository.ProductRepository;

/** Runs once at startup. @Profile("!prod") = active in every profile except "prod". */
@Component
@Profile("!prod")
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final ProductRepository repository;

    public DataSeeder(ProductRepository repository) {   // constructor injection: no @Autowired needed
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }
        repository.saveAll(List.of(
                new Product("Mechanical Keyboard", new BigDecimal("49.90"), 25),   // id 1
                new Product("Wireless Mouse", new BigDecimal("19.90"), 100),       // id 2
                new Product("4K Monitor", new BigDecimal("219.00"), 4),            // id 3 (low stock)
                new Product("USB-C Cable", new BigDecimal("4.50"), 0)));           // id 4 (out of stock)
        log.info("Seeded {} products", repository.count());
    }
}
