package com.example.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.order.client.ProductClient;
import com.example.order.client.ReserveRequest;
import com.example.order.common.ApiException;
import com.example.order.config.OrderProperties;
import com.example.order.domain.CustomerOrder;
import com.example.order.domain.OrderStatus;
import com.example.order.repository.OrderRepository;
import com.example.order.web.dto.CreateOrderRequest;
import com.example.order.web.dto.OrderDetailsResponse;
import com.example.order.web.dto.OrderResponse;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ProductClient productClient;
    private final OrderProperties properties;

    public OrderService(OrderRepository repository, ProductClient productClient, OrderProperties properties) {
        this.repository = repository;
        this.productClient = productClient;
        this.properties = properties;
    }

    /**
     * Deliberately NOT @Transactional: holding a DB connection while waiting on a network call is an anti-pattern.
     *
     * Interview talking point: this is a distributed operation with no shared transaction. If the reserve call
     * succeeds and the insert below fails, stock stays reserved. Real systems solve it with a saga /
     * compensating call ("release stock"), or the outbox pattern + messaging.
     */
    public OrderResponse placeOrder(CreateOrderRequest request) {
        if (request.quantity() > properties.maxQuantity()) {
            throw ApiException.badRequest("Maximum quantity per order is " + properties.maxQuantity());
        }
        var product = productClient.reserve(request.productId(), new ReserveRequest(request.quantity()));
        var order = repository.save(new CustomerOrder(product.id(), request.quantity(), product.price()));
        return OrderResponse.from(order);
    }

    public List<OrderResponse> findAll() {
        return repository.findAll().stream().map(OrderResponse::from).toList();
    }

    public OrderResponse findById(Long id) {
        return OrderResponse.from(getOrThrow(id));
    }

    /** Reads the order, then enriches it with product data. GET fallback degrades instead of failing. */
    public OrderDetailsResponse findDetails(Long id) {
        var order = getOrThrow(id);
        var product = productClient.getProduct(order.getProductId());
        return new OrderDetailsResponse(OrderResponse.from(order), product);
    }

    @Transactional
    public OrderResponse cancel(Long id) {
        var order = getOrThrow(id);
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw ApiException.conflict("Order " + id + " is already cancelled");
        }
        order.cancel();       // dirty checking persists the change at commit; no save() call needed
        return OrderResponse.from(order);
    }

    private CustomerOrder getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> ApiException.notFound("Order " + id + " not found"));
    }
}
