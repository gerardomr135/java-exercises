package com.example.order.web.dto;

import com.example.order.client.ProductDto;

/** An order enriched with live product data fetched from product-service (service-to-service call). */
public record OrderDetailsResponse(OrderResponse order, ProductDto product) {
}
