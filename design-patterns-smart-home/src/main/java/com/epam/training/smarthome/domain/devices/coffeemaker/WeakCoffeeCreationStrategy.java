package com.epam.training.smarthome.domain.devices.coffeemaker;

public class WeakCoffeeCreationStrategy implements CoffeeCreationStrategy{
    @Override
    public int getCaffeinelnMg() {
        return 20;
    }
}
