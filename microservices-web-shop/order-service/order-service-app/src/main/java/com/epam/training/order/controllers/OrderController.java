package com.epam.training.order.controllers;

import com.epam.training.order.api.OrderApi;
import com.epam.training.order.apimapper.OrderModelApiMapper;
import com.epam.training.order.apimodel.OrderAndPackageModel;
import com.epam.training.order.apimodel.OrderModel;
import com.epam.training.order.data.entity.Order;
import com.epam.training.order.data.repository.OrderRepository;
import com.epam.training.order.security.SecurityUtils;
import com.epam.training.shipping.api.ShippingApiClient;
import com.epam.training.shipping.apimodel.PackageModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderController implements OrderApi {

    private final OrderRepository orderRepository;
    private final OrderModelApiMapper apiMapper;
    private final ShippingApiClient shippingApiClient;

    @Override
    @Secured("ROLE_USER")
    public ResponseEntity<OrderModel> getOrder(@PathVariable("id") Long id) {
        log.info("getOrder has been called, user: {}, order id: {}", SecurityUtils.getAuthenticatedUsername(), id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order does not exist with id: " + id));

        OrderModel orderModel = apiMapper.map(order);
        return new ResponseEntity<>(orderModel, HttpStatus.OK);
    }

    @Override
    @Secured("ROLE_USER")
    public ResponseEntity<OrderAndPackageModel> getAggregatedOrder(@PathVariable("id") Long id) {
        log.info("getAggregatedOrder has been called, user: {}, order id: {}", SecurityUtils.getAuthenticatedUsername(), id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order does not exist with id: " + id));

        PackageModel packageModel = shippingApiClient.getPackage(order.getPackageId()).getBody();

        OrderAndPackageModel orderAndPackageModel = apiMapper.map(order, packageModel);
        return new ResponseEntity<>(orderAndPackageModel, HttpStatus.OK);
    }

}
