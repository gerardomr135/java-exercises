package com.example.order.web.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.example.order.domain.CustomerOrder;
import com.example.order.domain.OrderStatus;

public record OrderResponse(Long id, Long productId, int quantity, BigDecimal unitPrice,
                            BigDecimal total, OrderStatus status, Instant createdAt) {

    public static OrderResponse from(CustomerOrder o) {
        return new OrderResponse(o.getId(), o.getProductId(), o.getQuantity(), o.getUnitPrice(),
                o.getTotal(), o.getStatus(), o.getCreatedAt());
    }
}
