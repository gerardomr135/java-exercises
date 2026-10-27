package com.epam.training.food.controller;

import com.epam.training.food.api.FoodServiceApi;
import com.epam.training.food.domain.Food;
import com.epam.training.food.model.FoodModel;
import com.epam.training.food.service.FoodDeliveryService;
import com.epam.training.food.service.mapper.FoodMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/foods")
public class FoodController implements FoodServiceApi {
    private final FoodDeliveryService foodDeliveryService;

    public FoodController(FoodDeliveryService foodDeliveryService, FoodMapper foodMapper) {
        this.foodDeliveryService = foodDeliveryService;
    }

    @GetMapping
    @Override
    public ResponseEntity<List<FoodModel>> getFoods() {
        List<Food> foods = foodDeliveryService.listAllFood();
        List<FoodModel> res = foods.stream().map(FoodMapper::toFoodModel).toList();

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
