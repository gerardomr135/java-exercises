package com.epam.training.smarthome.domain.devices.coffeemaker;

import com.epam.training.smarthome.domain.observer.Observable;

public class CoffeeMaker extends Observable {
    private CoffeeCreationStrategy coffeeCreationStrategy;

    public CoffeeMaker(CoffeeCreationStrategy coffeeCreationStrategy) {
        this.coffeeCreationStrategy = coffeeCreationStrategy;
    }

    public void setCoffeeCreationStrategy(CoffeeCreationStrategy coffeeCreationStrategy) {
        notifyObservers("[CoffeeMaker] change the type of coffee");
        this.coffeeCreationStrategy = coffeeCreationStrategy;
    }

    public void createCoffee() {
        notifyObservers("[CoffeeMaker] create coffee with "+ coffeeCreationStrategy.getCaffeinelnMg() + "mg caffeine");
    }
}
