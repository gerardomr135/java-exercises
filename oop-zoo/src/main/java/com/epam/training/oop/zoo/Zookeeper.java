package com.epam.training.oop.zoo;

import com.epam.training.oop.zoo.animals.Animal;

public class Zookeeper {
    private final String name;
    private final Consumption[] consumption;

    public Zookeeper(String name, Consumption... consumption) {
        this.name = name;
        this.consumption = consumption;
    }

    public void feed(Animal[] animals) {
        for (Animal animal : animals) {
            if (!this.canGiveFood(animal)) {
                continue;
            }

            animal.makeSound();

            System.out.printf("%s is feeding %s the %s\n",
                    this.name,
                    animal.getName(),
                    animal.getClass().getSimpleName());

        }
    }

    private boolean canGiveFood(Animal animal) {
        for (Consumption consumption : this.consumption) {
            if (animal.getConsumption().equals(consumption)) {
                return true;
            }
        }

        return false;
    }

    public String getName() {
        return name;
    }
}
