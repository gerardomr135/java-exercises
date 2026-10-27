package com.example.product.web.dto;

import jakarta.validation.constraints.Positive;

public record ReserveRequest(@Positive int quantity) {
}
