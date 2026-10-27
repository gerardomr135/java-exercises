package com.example.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.product.common.ApiException;
import com.example.product.config.ProductProperties;
import com.example.product.domain.Product;
import com.example.product.repository.ProductRepository;

/** Plain unit test: no Spring context, repository mocked. Fastest kind of test. */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository repository;

    ProductService service;

    @BeforeEach
    void setUp() {
        service = new ProductService(repository, new ProductProperties(5));
    }

    @Test
    void reserve_decreasesStock() {
        var product = new Product("Keyboard", new BigDecimal("49.90"), 10);
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        var response = service.reserve(1L, 3);

        assertThat(response.stock()).isEqualTo(7);
    }

    @Test
    void reserve_failsWhenStockIsInsufficient() {
        var product = new Product("Keyboard", new BigDecimal("49.90"), 2);
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> service.reserve(1L, 3))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Insufficient stock");
    }

    @Test
    void findById_throwsNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("not found");
    }
}
