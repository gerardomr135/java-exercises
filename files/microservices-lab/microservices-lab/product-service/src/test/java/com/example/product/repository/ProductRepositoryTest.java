package com.example.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.product.domain.Product;

/** Slice test: JPA + an embedded H2 only. Each test rolls back automatically. */
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    @Test
    void findByNameContainingIgnoreCase_isCaseInsensitive() {
        repository.save(new Product("Mechanical Keyboard", new BigDecimal("89.00"), 5));
        repository.save(new Product("Mouse", new BigDecimal("19.00"), 3));

        assertThat(repository.findByNameContainingIgnoreCase("KEYBOARD")).hasSize(1);
    }

    @Test
    void findAffordableInStock_excludesOutOfStockAndTooExpensive() {
        repository.save(new Product("Cheap and available", new BigDecimal("10.00"), 5));
        repository.save(new Product("Cheap but sold out", new BigDecimal("5.00"), 0));
        repository.save(new Product("Too expensive", new BigDecimal("500.00"), 5));

        var result = repository.findAffordableInStock(new BigDecimal("50.00"));

        assertThat(result).extracting(Product::getName).containsExactly("Cheap and available");
    }
}
