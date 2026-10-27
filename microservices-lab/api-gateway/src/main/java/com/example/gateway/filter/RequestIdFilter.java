package com.example.gateway.filter;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * A GlobalFilter runs for every route. This one guarantees each request has an X-Request-Id
 * (generated if the client did not send one), forwards it downstream and echoes it back.
 */
@Component
public class RequestIdFilter implements GlobalFilter, Ordered {

    public static final String HEADER = "X-Request-Id";
    private static final Logger log = LoggerFactory.getLogger(RequestIdFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestId = Optional.ofNullable(exchange.getRequest().getHeaders().getFirst(HEADER))
                .orElseGet(() -> UUID.randomUUID().toString());

        // Exchanges are immutable-ish: mutate() gives a modified copy
        ServerWebExchange mutated = exchange.mutate()
                .request(r -> r.headers(h -> h.set(HEADER, requestId)))
                .build();
        mutated.getResponse().getHeaders().set(HEADER, requestId);

        log.info("--> {} {} requestId={}", exchange.getRequest().getMethod(),
                exchange.getRequest().getURI().getPath(), requestId);
        long start = System.nanoTime();

        return chain.filter(mutated).doFinally(signal ->
                log.info("<-- {} in {} ms requestId={}", mutated.getResponse().getStatusCode(),
                        (System.nanoTime() - start) / 1_000_000, requestId));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
