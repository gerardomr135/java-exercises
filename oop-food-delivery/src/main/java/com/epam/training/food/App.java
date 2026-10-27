package com.epam.training.food;

import com.epam.training.food.data.FileDataStore;
import com.epam.training.food.domain.Customer;
import com.epam.training.food.domain.Order;
import com.epam.training.food.service.AuthenticationException;
import com.epam.training.food.service.DefaultFoodDeliveryService;
import com.epam.training.food.service.FoodDeliveryService;
import com.epam.training.food.service.LowBalanceException;
import com.epam.training.food.values.FoodSelection;
import com.epam.training.food.view.CLIView;
import com.epam.training.food.view.View;

public class App {
    private static Customer authenticate(View view, DefaultFoodDeliveryService defaultFoodDeliveryService) {
        var credentials = view.readCredentials();

        Customer customer;
        try {
            customer = defaultFoodDeliveryService.authenticate(credentials);
        } catch (AuthenticationException e) {
            view.printErrorMessage("Invalid credentials");
            return null;
        }

        view.printWelcomeMessage(customer);

        return customer;
    }

    private static void createOrder(View view, Customer customer, FoodDeliveryService defaultFoodDeliveryService) {
        Order order;
        try {
            order = defaultFoodDeliveryService.createOrder(customer);
        } catch (IllegalStateException e) {
            view.printErrorMessage("Unable to create order with an empty cart.");
            return;
        }

        view.printOrderCreatedStatement(order, customer.getBalance());
    }

    public static void main(String[] args) {
        var fileDataStore = new FileDataStore("test");
        fileDataStore.init();

        var defaultFoodDeliveryService = new DefaultFoodDeliveryService(fileDataStore);
        var view = new CLIView();

        var customer = authenticate(view, defaultFoodDeliveryService);
        if (customer == null) {
            return;
        }

        var foods = defaultFoodDeliveryService.listAllFood();

        view.printAllFoods(foods);

        while (true) {
            FoodSelection foodSelection;
            try {
                foodSelection = view.readFoodSelection(foods);
            } catch (RuntimeException e) {
                view.printErrorMessage("Unable to read user input: " + e.getMessage());
                return;
            }

            if (foodSelection == FoodSelection.NONE) {
                break;
            }

            view.printAddedToCart(foodSelection.food(), foodSelection.amount());

            try {
                defaultFoodDeliveryService.updateCart(customer, foodSelection.food(), foodSelection.amount());
            } catch (LowBalanceException e) {
                view.printErrorMessage(
                        String.format(
                                "Unable to add current order for %s, as with current cart content it would exceed available balance!",
                                foodSelection.food().toString()));
                return;
            }
        }

        createOrder(view, customer, defaultFoodDeliveryService);
    }
}
