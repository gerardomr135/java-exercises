package com.epam.training.restaurant.configuration;

import com.epam.training.restaurant.data.FoodRepository;
import com.epam.training.restaurant.domain.Food;
import dev.langchain4j.model.input.PromptTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SystemMessageProvider implements Function<Object, String> { // Implement Function

    private final FoodRepository foodRepository;

    public SystemMessageProvider(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override // Implement the apply method
    public String apply(Object chatMemoryId) { // Change method signature and return type
        // 1. Load the template from the resources folder
        String templateString = loadTemplate();

        // 2. Retrieve and format dynamic food data
        List<Food> foods = foodRepository.getAllFoods();
        String foodsText = formatFoods(foods);

        // 3. Resolve the {{foods}} variable using LangChain4j's PromptTemplate
        PromptTemplate promptTemplate = PromptTemplate.from(templateString);
        return promptTemplate.apply(Map.of("foods", foodsText)).text(); // Return String
    }

    /**
     * Reads the content of a resource file into a String.
     */
    private String loadTemplate() {
        try (InputStream is = getClass().getResourceAsStream("/system-prompt-template.txt")) {
            if (is == null) {
                throw new RuntimeException("Template file not found: " + "/system-prompt-template.txt");
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error reading template file", e);
        }
    }

    /**
     * Formats the list of Food objects into a readable multiline text.
     */
    private String formatFoods(List<Food> foods) {
        return foods.stream()
                .map(food -> String.format("- %s (Ingredients: %s, Allergens: %s)",
                        food.getName(),
                        String.join(", ", food.getIngredients()),
                        food.getAllergens()))
                .collect(Collectors.joining("\n"));
    }
}