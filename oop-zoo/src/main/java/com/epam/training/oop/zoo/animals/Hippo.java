package com.epam.training.oop.zoo.animals;

import com.epam.training.oop.zoo.Consumption;

public class Hippo extends Animal {
    private String name;

    public Hippo(String name) {
        super(name, Consumption.HERBIVORES);
        this.name = name;
    }

    @Override
    public void makeSound() {
        System.out.printf("%s the %s barks\n",
                this.name,
                this.getClass().getSimpleName());
    }
}
