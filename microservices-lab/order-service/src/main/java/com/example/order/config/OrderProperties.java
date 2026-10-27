package com.example.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/** Bound from "order.*" in config-repo/order-service.yml. */
@ConfigurationProperties(prefix = "order")
public record OrderProperties(@DefaultValue("10") int maxQuantity) {
}
