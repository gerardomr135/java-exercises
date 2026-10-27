package com.epam.training.oop.zoo.animals;

import com.epam.training.oop.zoo.Consumption;

public class Mandrill extends Animal {
    private String name;

    public Mandrill(String name) {
        super(name, Consumption.OMNIVORES);
        this.name = name;
    }

    @Override
    public void makeSound() {
        System.out.printf("%s the %s screams\n",
                this.name,
                this.getClass().getSimpleName());
    }
}
