package com.epam.training.food;

import com.epam.training.food.data.DataStore;
import com.epam.training.food.domain.Customer;
import com.epam.training.food.domain.Order;
import com.epam.training.food.service.AuthenticationException;
import com.epam.training.food.service.FoodDeliveryService;
import com.epam.training.food.service.LowBalanceException;
import com.epam.training.food.values.FoodSelection;
import com.epam.training.food.view.View;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Hello world!
 *
 */
@Component
public class Application implements CommandLineRunner {
    private final FoodDeliveryService foodDeliveryService;
    private final View view;

    public Application(FoodDeliveryService service, View view) {
        this.foodDeliveryService = service;
        this.view = view;
    }

    @Override
    public void run(String... args) throws Exception {
        var customer = authenticate();
        if (customer == null) {
            return;
        }

        var foods = foodDeliveryService.listAllFood();

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
                foodDeliveryService.updateCart(customer, foodSelection.food(), foodSelection.amount());
            } catch (LowBalanceException e) {
                view.printErrorMessage(
                        String.format(
                                "Unable to add current order for %s, as with current cart content it would exceed available balance!",
                                foodSelection.food().toString()));
                return;
            }
        }

        createOrder(customer);
    }

    private Customer authenticate() {
        var credentials = view.readCredentials();

        Customer customer;
        try {
            customer = foodDeliveryService.authenticate(credentials);
        } catch (AuthenticationException e) {
            view.printErrorMessage("Invalid credentials");
            return null;
        }

        view.printWelcomeMessage(customer);

        return customer;
    }

    private void createOrder(Customer customer) {
        Order order;
        try {
            order = foodDeliveryService.createOrder(customer);
        } catch (IllegalStateException e) {
            view.printErrorMessage("Unable to create order with an empty cart.");
            return;
        }

        view.printOrderCreatedStatement(order, customer.getBalance());
    }
}
