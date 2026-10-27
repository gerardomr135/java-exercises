package com.epam.training.restaurant.services;

import com.epam.training.restaurant.data.FoodRepository;
import com.epam.training.restaurant.domain.Food;
import com.epam.training.restaurant.domain.OrderItem;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantAssistantTools {

    private final FoodRepository foodRepository;
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Tool("Adds a specified quantity of a food item to the customer's order")
    public String addToOrder(
            @P("name of the food item to add to the order") String foodName,
            @P("quantity of the food item") int quantity
    ) {
        log.info("Attempting to add {} of {} to order", quantity, foodName);

        if (quantity <= 0) {
            return "Quantity must be positive.";
        }

        Optional<Food> foodOptional = foodRepository.getAllFoods().stream()
                .filter(food -> food.getName().equalsIgnoreCase(foodName))
                .findFirst();

        if (foodOptional.isEmpty()) {
            return String.format("Sorry, '%s' is not on our menu.", foodName);
        }

        Food food = foodOptional.get();
        Optional<OrderItem> existingOrderItem = orderItems.stream()
                .filter(item -> item.getFood().equals(food))
                .findFirst();

        if (existingOrderItem.isPresent()) {
            existingOrderItem.get().setQuantity(existingOrderItem.get().getQuantity() + quantity);
            log.info("Updated quantity for {}. Current order: {}", foodName, orderItems);
            return String.format("Updated quantity for %s. You now have %d of them.", foodName, existingOrderItem.get().getQuantity());
        } else {
            orderItems.add(new OrderItem(food, quantity));
            log.info("Added {} to order. Current order: {}", foodName, orderItems);
            return String.format("Added %d %s to your order.", quantity, foodName);
        }
    }

    @Tool("Submits the current order for processing")
    public String submitOrder() {
        log.info("Attempting to submit order. Current order: {}", orderItems);

        if (orderItems.isEmpty()) {
            return "Your order is empty. Please add some items before submitting.";
        }

        // In a real application, this would involve complex logic:
        // - Payment processing
        // - Sending order to kitchen
        // - Generating order confirmation
        // For this demonstration, we'll just validate and log.

        // Clear the order after submission (for demonstration purposes)
        orderItems.clear();
        log.info("Order submitted successfully. Order cleared.");
        return "Success! Your order has been placed and will be prepared shortly.";
    }
}