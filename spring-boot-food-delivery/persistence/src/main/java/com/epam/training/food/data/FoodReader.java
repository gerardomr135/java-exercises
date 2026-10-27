package com.epam.training.food.data;

import com.epam.training.food.domain.Food;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FoodReader extends Reader {

    @Override public List<Food> read(String pathOfFile) {
        List<String> lines = readFromFile(pathOfFile);
        ArrayList<Food> foods = new ArrayList<Food>(lines.size());
        for (String s : lines) {
            foods.add(buildFood(s));
        }
        return foods;
    }

    private Food buildFood(String line) {
        String[] strings = splitLineByComma(line);
        return new Food.Builder()
            .name(strings[0])
            .calorie(new BigDecimal(strings[1]))
            .description(strings[2])
            .price(new BigDecimal(strings[3]))
            .build();
    }
}
