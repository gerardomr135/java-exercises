package com.example.order.client;

import java.math.BigDecimal;

/** What order-service needs from product-service (its own copy of the contract, not a shared class). */
public record ProductDto(Long id, String name, BigDecimal price, int stock) {
}
