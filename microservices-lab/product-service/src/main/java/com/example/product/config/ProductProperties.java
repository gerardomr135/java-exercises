package com.example.product.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Typed, immutable configuration bound from "product.*" (values come from the Config Server).
 * Prefer this over scattering @Value strings. Relaxed binding: product.low-stock-threshold -> lowStockThreshold.
 */
@ConfigurationProperties(prefix = "product")
public record ProductProperties(@DefaultValue("5") int lowStockThreshold) {
}
