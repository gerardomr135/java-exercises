package com.example.order.client;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.example.order.common.ApiException;

import feign.FeignException;

/**
 * FallbackFactory (vs plain fallback) gives access to the CAUSE of the failure.
 *
 * Design rule worth remembering for interviews:
 *  - READS may degrade gracefully (return a placeholder).
 *  - WRITES must fail explicitly. A "fake success" fallback for reserve() would create orders for stock
 *    that was never reserved.
 */
@Component
public class ProductClientFallbackFactory implements FallbackFactory<ProductClient> {

    private static final Logger log = LoggerFactory.getLogger(ProductClientFallbackFactory.class);

    @Override
    public ProductClient create(Throwable cause) {
        return new ProductClient() {

            @Override
            public ProductDto getProduct(Long id) {
                if (unwrap(cause) instanceof FeignException.NotFound) {
                    throw ApiException.notFound("Product " + id + " does not exist");
                }
                log.warn("product-service unavailable, returning degraded product {}: {}", id, cause.toString());
                return new ProductDto(id, "unavailable", BigDecimal.ZERO, 0);
            }

            @Override
            public ProductDto reserve(Long id, ReserveRequest request) {
                throw translate(id, cause);
            }
        };
    }

    /** Java 21 pattern-matching switch over the exception type. */
    private static ApiException translate(Long id, Throwable cause) {
        return switch (unwrap(cause)) {
            case FeignException.NotFound e -> ApiException.notFound("Product " + id + " does not exist");
            case FeignException.Conflict e -> ApiException.conflict("Product " + id + " has insufficient stock");
            default -> ApiException.unavailable("product-service is unavailable, please try again later", cause);
        };
    }

    /** Circuit-breaker wrappers may nest the original FeignException; find it in the cause chain. */
    private static Throwable unwrap(Throwable throwable) {
        for (Throwable t = throwable; t != null; t = t.getCause()) {
            if (t instanceof FeignException) {
                return t;
            }
        }
        return throwable;
    }
}
