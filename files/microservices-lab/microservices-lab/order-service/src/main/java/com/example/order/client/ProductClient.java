package com.example.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Declarative REST client. "product-service" is NOT a URL: it is the Eureka service id.
 * Feign asks Spring Cloud LoadBalancer for an instance (round-robin) and calls it.
 * fallbackFactory runs when the call fails, times out, or the circuit is open.
 */
@FeignClient(name = "product-service", fallbackFactory = ProductClientFallbackFactory.class)
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductDto getProduct(@PathVariable("id") Long id);

    @PostMapping("/api/products/{id}/reserve")
    ProductDto reserve(@PathVariable("id") Long id, @RequestBody ReserveRequest request);
}
