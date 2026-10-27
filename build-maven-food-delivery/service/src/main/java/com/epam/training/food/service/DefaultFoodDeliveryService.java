package com.epam.training.food.service;

import com.epam.training.food.data.DataStore;
import com.epam.training.food.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DefaultFoodDeliveryService implements FoodDeliveryService {
    private final DataStore dataStore;

    public DefaultFoodDeliveryService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Customer authenticate(Credentials credentials) throws AuthenticationException {
        var optCustomer = dataStore.getCustomers()
                .stream()
                .filter((Customer customer) -> {
                    return credentials.getUserName().equals(customer.getUserName()) &&
                            credentials.getPassword().equals(customer.getPassword());
                })
                .findFirst();

        if (optCustomer.isPresent()) {
            return optCustomer.get();
        }

        throw new AuthenticationException("Customer not found");
    }

    @Override
    public List<Food> listAllFood() {
        return dataStore.getFoods();
    }

    @Override
    public void updateCart(Customer customer, Food food, int pieces) throws LowBalanceException {
        validatePieces(pieces);
        validateBalance(customer, food, pieces);

        var cart = customer.getCart();
        var cartItemOpt = cart.findOrderItemByFoodName(food.getName());
        var isPresentInCart = cartItemOpt.isPresent();

        validateCartOperation(isPresentInCart, pieces);
        performCartOperation(cart, food, pieces, isPresentInCart);
    }

    private void validatePieces(int pieces) {
        if (pieces < 0) {
            throw new IllegalArgumentException("Pieces cannot be negative");
        }
    }

    private void validateBalance(Customer customer, Food food, int pieces) throws LowBalanceException {
        var orderItemTotal = food.getPrice().multiply(new BigDecimal(pieces));
        if (orderItemTotal.compareTo(customer.getBalance()) > 0) {
            throw new LowBalanceException("Order item exceed balance.");
        }
    }

    private void validateCartOperation(boolean isPresentInCart, int pieces) {
        if (!isPresentInCart && pieces == 0) {
            throw new IllegalArgumentException("Food not in cart and pieces is 0");
        }
    }

    private void performCartOperation(com.epam.training.food.domain.Cart cart, Food food, int pieces,
            boolean isPresentInCart) {
        if (isPresentInCart && pieces == 0) {
            cart.removeOrderItemByFoodName(food.getName());
            return;
        }

        if (isPresentInCart) {
            var orderItem = new OrderItem(null, pieces, null);
            cart.updateOrderItemByFoodName(food.getName(), orderItem);
            return;
        }

        var orderItemTotal = food.getPrice().multiply(new BigDecimal(pieces));
        cart.addOrderItem(new OrderItem(food, pieces, orderItemTotal));
    }

    @Override
    public Order createOrder(Customer customer) throws IllegalStateException {
        var cart = customer.getCart();

        // Replace Nested Conditional with Guard Clauses
        if (cart.getOrderItems().isEmpty()) {
            throw new IllegalStateException("Unable to create an order with an empty cart.");
        }

        var newOrder = new Order(
                0L,
                customer.getId(),
                cart.getOrderItems(),
                cart.getPrice(),
                LocalDateTime.now());

        customer.getOrders().add(newOrder);

        customer.setBalance(customer.getBalance().subtract(cart.getPrice()));

        cart.setOrderItems(new ArrayList<>());

        return dataStore.createOrder(newOrder);
    }
}
