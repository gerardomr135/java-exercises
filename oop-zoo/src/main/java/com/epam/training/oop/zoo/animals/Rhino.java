package com.epam.training.oop.zoo.animals;

import com.epam.training.oop.zoo.Consumption;

public class Rhino extends Animal {
    private String name;

    public Rhino(String name) {
        super(name, Consumption.HERBIVORES);
        this.name = name;
    }

    @Override
    public void makeSound() {
        System.out.printf("%s the %s moos\n",
                this.name,
                this.getClass().getSimpleName());
    }
}
