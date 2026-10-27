package com.epam.training.restaurant.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter @Setter
public class Food {
    private int id;
    private String name;
    private List<String> ingredients;
    private List<Allergen> allergens;
}
