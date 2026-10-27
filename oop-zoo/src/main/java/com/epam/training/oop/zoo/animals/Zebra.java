package com.epam.training.oop.zoo.animals;

import com.epam.training.oop.zoo.Consumption;

public class Zebra extends Animal {
    private String name;

    public Zebra(String name) {
        super(name, Consumption.HERBIVORES);
        this.name = name;
    }

    @Override
    public void makeSound() {
        System.out.printf("%s the %s brays\n",
                this.name,
                this.getClass().getSimpleName());
    }
}
