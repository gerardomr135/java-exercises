package com.epam.training.food.service;

import com.epam.training.food.domain.*;
import com.epam.training.food.repository.CustomerRepository;
import com.epam.training.food.repository.FoodRepository;
import com.epam.training.food.repository.OrderRepository;
import com.epam.training.food.service.exceptions.AuthenticationException;
import com.epam.training.food.service.exceptions.LowBalanceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultFoodDeliveryService implements FoodDeliveryService {
    private final CustomerRepository customerRepository;
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;

    public DefaultFoodDeliveryService(CustomerRepository customerRepository, FoodRepository foodRepository,
            OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.foodRepository = foodRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public Customer authenticate(Credentials credentials) throws AuthenticationException {
        return customerRepository.findByUserNameAndPassword(
                credentials.getUserName(), credentials.getPassword())
                .orElseThrow(() -> new AuthenticationException("Customer not found"));
    }

    @Transactional
    @Override
    public List<Food> listAllFood() {
        return foodRepository.findAll();
    }

    @Transactional
    @Override
    public void updateCart(Customer customer, Food food, int pieces) throws LowBalanceException {
        validatePieces(pieces);
        validateBalance(customer, food, pieces);

        Cart cart = customer.getCart();
        Optional<OrderItem> cartItemOpt = cart.findOrderItemByFoodName(food.getName());
        boolean isPresentInCart = cartItemOpt.isPresent();

        validateCartOperation(isPresentInCart, pieces);
        performCartOperation(cart, food, pieces, isPresentInCart);
    }

    private void validatePieces(int pieces) {
        if (pieces < 0) {
            throw new IllegalArgumentException("Pieces cannot be negative");
        }
    }

    private void validateBalance(Customer customer, Food food, int pieces) throws LowBalanceException {
        BigDecimal orderItemTotal = food.getPrice().multiply(new BigDecimal(pieces));
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
            OrderItem orderItem = new OrderItem(null, pieces, BigDecimal.ZERO);
            cart.updateOrderItemByFoodName(food.getName(), orderItem);
            return;
        }

        BigDecimal orderItemTotal = food.getPrice().multiply(new BigDecimal(pieces));
        cart.addOrderItem(new OrderItem(food, pieces, orderItemTotal));
    }

    @Override
    @Transactional
    public Order createOrder(Customer customer) throws IllegalStateException {
        Cart cart = customer.getCart();

        if (cart.getOrderItems().isEmpty()) {
            throw new IllegalStateException("Unable to create an order with an empty cart.");
        }

        Customer managedCustomer = customerRepository.findByUserName(customer.getUserName())
                .orElseThrow(() -> new IllegalStateException("Customer not found"));

        if (cart.getPrice().compareTo(managedCustomer.getBalance()) > 0) {
            throw new LowBalanceException("Order exceed balance.");
        }

        List<OrderItem> newOrderItems = cart.getOrderItems().stream()
                .map(cartItem -> {
                    OrderItem newItem = new OrderItem();
                    newItem.setFood(cartItem.getFood());
                    newItem.setPieces(cartItem.getPieces());
                    newItem.setPrice(cartItem.getPrice());
                    return newItem;
                })
                .toList();

        Order newOrder = new Order(
                managedCustomer.getId(),
                newOrderItems,
                cart.getPrice(),
                LocalDateTime.now());

        managedCustomer.setBalance(managedCustomer.getBalance().subtract(cart.getPrice()));
        managedCustomer.getOrders().add(newOrder);

        return newOrder;
    }
}
