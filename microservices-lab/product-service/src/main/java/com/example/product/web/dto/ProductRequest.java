package com.example.product.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/** Java 21 record as an immutable DTO; Bean Validation annotations are checked by @Valid in the controller. */
public record ProductRequest(
        @NotBlank @Size(max = 100) String name,
        @NotNull @Positive BigDecimal price,
        @Min(0) int stock) {
}
