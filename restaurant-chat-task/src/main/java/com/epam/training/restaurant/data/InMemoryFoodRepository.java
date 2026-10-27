package com.epam.training.restaurant.data;

import com.epam.training.restaurant.domain.Food;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.epam.training.restaurant.domain.Allergen.*;

@Component
public class InMemoryFoodRepository implements FoodRepository {
    private final List<Food> foods;

    public InMemoryFoodRepository() {
        foods = createFoods();
    }

    private static List<Food> createFoods() {
        return List.of(
                Food.builder()
                        .id(1)
                        .name("Pasta al Pesto")
                        .ingredients(List.of("Pasta", "Basil", "Olive oil", "Pine nuts", "Parmesan cheese", "Garlic"))
                        .allergens(List.of(GLUTEN, LACTOSE, NUTS))
                        .build(),
                Food.builder()
                        .id(2)
                        .name("Eggplant Parmigiana")
                        .ingredients(List.of("Eggplant", "Tomato sauce", "Mozzarella", "Parmesan", "Breadcrumbs"))
                        .allergens(List.of(GLUTEN, LACTOSE))
                        .build(),
                Food.builder()
                        .id(3)
                        .name("Mushroom Risotto")
                        .ingredients(List.of("Arborio rice", "Mushrooms", "Vegetable broth", "Butter", "Parmesan"))
                        .allergens(List.of(LACTOSE))
                        .build(),
                Food.builder()
                        .id(4)
                        .name("Margherita Pizza")
                        .ingredients(List.of("Pizza dough (wheat flour)", "Tomato sauce", "Mozzarella cheese", "Basil", "Olive oil"))
                        .allergens(List.of(GLUTEN, LACTOSE))
                        .build()
        );
    }

    public List<Food> getAllFoods() {
        return foods;
    }

    @Override
    public Optional<Food> findById(int id) {
        return foods.stream()
                .filter(food -> food.getId() == id)
                .findFirst();
    }

}
