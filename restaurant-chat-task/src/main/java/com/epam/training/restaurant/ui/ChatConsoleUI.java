package com.epam.training.restaurant.ui;

import com.epam.training.restaurant.services.RestaurantChatService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ChatConsoleUI implements CommandLineRunner {

    private final RestaurantChatService restaurantChatService;

    public ChatConsoleUI(RestaurantChatService restaurantChatService) {
        this.restaurantChatService = restaurantChatService;
    }

    @Override
    public void run(String... args) {
        System.out.println("\n=====================================================");
        System.out.println("Welcome to the Restaurant Chatbot!");
        System.out.println("You can ask me about our available foods, ingredients, and allergens.");
        System.out.println("Type '/quit' to exit.");
        System.out.println("=====================================================\n");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("You: ");
                String input = scanner.nextLine();

                if ("/quit".equalsIgnoreCase(input.trim())) {
                    System.out.println("Bot: Goodbye! Have a great day.");
                    break;
                }

                if (!input.trim().isEmpty()) {
                    System.out.print("Bot: Thinking...");

                    try {
                        String response = restaurantChatService.chat(input);
                        // Print \r to overwrite "Thinking..."
                        System.out.print("\rBot: " + response + "\n\n");
                    } catch (Exception e) {
                        System.out.print("\rBot: Sorry, I encountered an error. (" + e.getMessage() + ")\n\n");
                    }
                }
            }
        }
    }
}
