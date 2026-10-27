package com.example.order.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.example.order.common.ApiException;

class ProductClientFallbackFactoryTest {

    private final ProductClientFallbackFactory factory = new ProductClientFallbackFactory();

    @Test
    void reserve_failsExplicitlyWith503WhenServiceIsDown() {
        var client = factory.create(new RuntimeException("connection refused"));

        assertThatThrownBy(() -> client.reserve(1L, new ReserveRequest(1)))
                .isInstanceOfSatisfying(ApiException.class,
                        e -> assertThat(e.getStatus()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE));
    }

    @Test
    void getProduct_degradesGracefullyWhenServiceIsDown() {
        var client = factory.create(new RuntimeException("connection refused"));

        var product = client.getProduct(7L);

        assertThat(product.id()).isEqualTo(7L);
        assertThat(product.name()).isEqualTo("unavailable");
    }
}
