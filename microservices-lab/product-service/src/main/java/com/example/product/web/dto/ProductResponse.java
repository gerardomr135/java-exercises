package com.example.product.web.dto;

import java.math.BigDecimal;

import com.example.product.domain.Product;

/** Never expose JPA entities from controllers: the DTO is the API contract. */
public record ProductResponse(Long id, String name, BigDecimal price, int stock) {

    public static ProductResponse from(Product p) {
        return new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getStock());
    }
}
