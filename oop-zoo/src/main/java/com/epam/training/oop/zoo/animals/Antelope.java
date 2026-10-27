package com.epam.training.oop.zoo.animals;

import com.epam.training.oop.zoo.Consumption;

public class Antelope extends Animal {
    private String name;

    public Antelope(String name) {
        super(name, Consumption.HERBIVORES);
        this.name = name;
    }

    @Override
    public void makeSound() {
        System.out.printf("%s the %s snorts\n",
                this.name,
                this.getClass().getSimpleName());
    }
}
