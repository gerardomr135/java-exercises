package com.epam.training.food.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Cart {
    private List<OrderItem> orderItems;
    // the field price can be used later to add discounts, so it would differ from
    // the sum of the OrderItems
    private BigDecimal price;

    public Cart() {
    }

    private Cart(List<OrderItem> orderItems, BigDecimal price) {
        this.orderItems = orderItems;
        this.price = price;
    }

    // this utility method is not part of the homework specification!
    public static Cart getEmptyCart() {
        return new Cart(new ArrayList<>(), BigDecimal.ZERO);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Optional<OrderItem> findOrderItemByFoodName(String name) {
        return getOrderItems()
                .stream()
                .filter((OrderItem orderItem) -> {
                    return orderItem.getFood().getName().equals(name);
                })
                .findFirst();
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        calculateTotalCart();
    }

    private void calculateTotalCart() {
        var cartTotal = BigDecimal.ZERO;

        for (OrderItem orderItem : orderItems) {
            var orderItemTotal = orderItem.getFood().getPrice().multiply(new BigDecimal(orderItem.getPieces()));

            orderItem.setPrice(orderItemTotal);
            cartTotal = cartTotal.add(orderItemTotal);
        }

        setPrice(cartTotal);
    }

    public void updateOrderItemByFoodName(String foodName, OrderItem orderItem) {
        for (OrderItem oi : getOrderItems()) {
            if (oi.getFood().getName().equals(foodName)) {
                if (orderItem.getPieces() != 0) {
                    oi.setPieces(orderItem.getPieces());
                }

                if (orderItem.getPrice() != null) {
                    oi.setPrice(orderItem.getPrice());
                }
            }
        }

        calculateTotalCart();
    }

    public void removeOrderItemByFoodName(String foodName) {
        var newOrderItems = getOrderItems()
                .stream()
                .filter((OrderItem orderItem) -> {
                    return !orderItem.getFood().getName().equals(foodName);
                })
                .toList();

        setOrderItems(newOrderItems);
        calculateTotalCart();
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cart cart = (Cart) o;
        return Objects.equals(orderItems, cart.orderItems) && Objects.equals(price, cart.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItems, price);
    }

}
