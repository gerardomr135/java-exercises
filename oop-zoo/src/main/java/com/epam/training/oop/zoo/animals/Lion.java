package com.epam.training.oop.zoo.animals;

import com.epam.training.oop.zoo.Consumption;

public class Lion extends Animal {
    private String name;

    public Lion(String name) {
        super(name, Consumption.CARNIVORES);
        this.name = name;
    }

    @Override
    public void makeSound() {
        System.out.printf("%s the %s roars\n",
                this.name,
                this.getClass().getSimpleName());
    }
}
