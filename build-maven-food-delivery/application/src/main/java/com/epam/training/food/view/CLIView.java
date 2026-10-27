package com.epam.training.food.view;

import com.epam.training.food.domain.*;
import com.epam.training.food.service.AuthenticationException;
import com.epam.training.food.service.FoodDeliveryService;
import com.epam.training.food.values.FoodSelection;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLIView implements View {
    private final Scanner scanner;

    public CLIView() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Constructs a CLIView instance.
     * 
     * Note: This constructor works with Dependency Inversion (D) to reduce code
     * coupling and at the same time provide dependency injection.
     * It is not used to keep README specifitations.
     */
    public CLIView(FoodDeliveryService foodDeliveryService) {
        this.scanner = new Scanner(System.in);
        // this.foodDeliveryService = foodDeliveryService;
    }

    @Override
    public Credentials readCredentials() throws AuthenticationException {
        System.out.print("Enter customer name:");
        var customerName = scanner.nextLine();

        System.out.print("Enter customer password:");
        var customerPassword = scanner.nextLine();

        return new Credentials(customerName, customerPassword);
    }

    @Override
    public void printWelcomeMessage(Customer customer) {
        System.out.printf("Welcome, %s. Your balance is: %s EUR.%n",
                customer.getName(),
                customer.getBalance().toString());
        System.out.println();
    }

    @Override
    public void printAllFoods(List<Food> foods) {
        for (Food food : foods) {
            System.out.printf("- %s %s EUR each%n",
                    food.getName(),
                    food.getPrice().toString());
        }
        System.out.println();
    }

    @Override
    public FoodSelection readFoodSelection(List<Food> foods) throws RuntimeException {
        System.out.print("Please enter the name and amount of food (separated by comma) you would like to buy:");
        var selection = scanner.nextLine();

        if (selection.isBlank()) {
            return FoodSelection.NONE;
        }

        var selectionParts = selection.split(",");

        if (selectionParts.length <= 1) {
            throw new RuntimeException("Selection format not followed");
        }

        var foodOpt = foods.stream()
                .filter((Food food) -> {
                    return food.getName().equals(selectionParts[0]);
                })
                .findFirst();

        if (foodOpt.isEmpty()) {
            throw new RuntimeException("Food Not Found");
        }

        return new FoodSelection(foodOpt.get(), Integer.parseInt(selectionParts[1]));
    }

    @Override
    public void printAddedToCart(Food food, int pieces) {
        System.out.printf("Added %d piece(s) of %s to the cart.%n",
                pieces,
                food.getName());
        System.out.println();
    }

    @Override
    public void printErrorMessage(String message) {
        System.err.println(message);
    }

    @Override
    public void printOrderCreatedStatement(Order order, BigDecimal balance) {
        var itemsName = new ArrayList<String>(order.getOrderItems().size());

        for (OrderItem orderItem : order.getOrderItems()) {
            itemsName.add(orderItem.getFood().getName());
        }

        System.out.printf(
                "Order (items: [%s], price: %s EUR, timestamp: %s) has been confirmed.%n",
                String.join(",", itemsName),
                order.getPrice(),
                order.getTimestampCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        System.out.printf("Your balance is %s EUR.%n", balance.toString());
        System.out.println("Thank you for your purchase.");
        scanner.close();
    }
}
