package com.epam.training.restaurant.data;

import com.epam.training.restaurant.domain.Food;

import java.util.List;
import java.util.Optional;

public interface FoodRepository {
    List<Food> getAllFoods();
    Optional<Food> findById(int id);
}
