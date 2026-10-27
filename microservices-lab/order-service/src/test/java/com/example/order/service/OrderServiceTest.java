package com.example.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.order.client.ProductClient;
import com.example.order.client.ProductDto;
import com.example.order.client.ReserveRequest;
import com.example.order.common.ApiException;
import com.example.order.config.OrderProperties;
import com.example.order.domain.CustomerOrder;
import com.example.order.domain.OrderStatus;
import com.example.order.repository.OrderRepository;
import com.example.order.web.dto.CreateOrderRequest;

/** The Feign client is just an interface, so mocking the other microservice is trivial. */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository repository;

    @Mock
    ProductClient productClient;

    OrderService service;

    @BeforeEach
    void setUp() {
        service = new OrderService(repository, productClient, new OrderProperties(10));
    }

    @Test
    void placeOrder_reservesStockAndPersistsOrder() {
        when(productClient.reserve(eq(1L), any(ReserveRequest.class)))
                .thenReturn(new ProductDto(1L, "Keyboard", new BigDecimal("50.00"), 8));
        when(repository.save(any(CustomerOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = service.placeOrder(new CreateOrderRequest(1L, 2));

        assertThat(response.total()).isEqualByComparingTo("100.00");
        assertThat(response.status()).isEqualTo(OrderStatus.CONFIRMED);
    }

    @Test
    void placeOrder_rejectsQuantityAboveConfiguredLimit() {
        assertThatThrownBy(() -> service.placeOrder(new CreateOrderRequest(1L, 11)))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("Maximum quantity");

        verifyNoInteractions(productClient);     // failed fast, never called the other service
    }
}
