package com.epam.training.food.controller;

import com.epam.training.food.domain.Customer;
import com.epam.training.food.domain.Order;
import com.epam.training.food.model.CartModel;
import com.epam.training.food.model.OrderModel;
import com.epam.training.food.repository.OrderRepository;
import com.epam.training.food.service.FoodDeliveryService;
import com.epam.training.food.service.mapper.FoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    FoodDeliveryService foodDeliveryService;

    @GetMapping
    public ResponseEntity<List<OrderModel>> listOrders(@AuthenticationPrincipal User currentUser) {
        List<Order> orders = orderRepository.findByCustomerUserName(currentUser.getUsername());
        List<OrderModel> res = orders.stream().map(FoodMapper::toOrderModel).toList();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderModel> getOrderById(@PathVariable(name = "id") String orderId,
            @AuthenticationPrincipal User currentUser) throws AccessDeniedException {
        if (!orderRepository.existsByCustomerUserName(currentUser.getUsername())) {
            throw new AccessDeniedException("You are not allowed to access this resource");
        }

        Optional<Order> orderOpt = orderRepository.findByCustomerUserNameAndId(currentUser.getUsername(),
                Long.parseLong(orderId));
        if (orderOpt.isEmpty()) {
            throw new NoSuchElementException("Order not found");
        }

        return new ResponseEntity<>(FoodMapper.toOrderModel(orderOpt.get()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderModel> createOrder(@RequestBody CartModel cartModel,
            @AuthenticationPrincipal User currentUser) {
        Customer customer = new Customer();
        customer.setCart(FoodMapper.toCart(cartModel));
        customer.setUserName(currentUser.getUsername());
        customer.setPassword(currentUser.getPassword());

        Order order;
        try {
            order = foodDeliveryService.createOrder(customer);
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("Car empty");
        }

        return new ResponseEntity<>(FoodMapper.toOrderModel(order), HttpStatus.CREATED);
    }
}
