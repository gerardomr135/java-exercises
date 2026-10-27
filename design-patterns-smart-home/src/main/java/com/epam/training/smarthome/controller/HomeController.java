package com.epam.training.smarthome.controller;

import com.epam.training.smarthome.domain.devices.AlarmSystem;
import com.epam.training.smarthome.domain.devices.FrontDoor;
import com.epam.training.smarthome.domain.devices.Light;
import com.epam.training.smarthome.domain.devices.coffeemaker.CoffeeMaker;
import com.epam.training.smarthome.domain.devices.coffeemaker.StrongCoffeeCreationStrategy;
import com.epam.training.smarthome.domain.devices.coffeemaker.WeakCoffeeCreationStrategy;
import com.epam.training.smarthome.domain.devices.heatingsystem.HeatingSystem;
import com.epam.training.smarthome.domain.devices.heatingsystem.HeatingSystemAdapter;
import com.epam.training.smarthome.domain.devices.heatingsystem.LegacyHeatingSystem;
import com.epam.training.smarthome.domain.observer.Observer;

import java.util.Objects;

public class HomeController {
    private final AlarmSystem alarmSystem;
    private final FrontDoor frontDoor;
    private final HeatingSystem heatingSystem;
    private final Light light;
    private final CoffeeMaker coffeeMaker;

    HomeController(HomeControllerBuilder homeControllerBuilder) {
        this.alarmSystem = Objects.requireNonNullElseGet(homeControllerBuilder.alarmSystem, () -> {
            AlarmSystem alarmSystem = new AlarmSystem(false);
            alarmSystem.addObserver(homeControllerBuilder.messageObserver);
            return alarmSystem;
        });

        this.frontDoor = Objects.requireNonNullElseGet(homeControllerBuilder.frontDoor, () -> {
            FrontDoor frontDoor = new FrontDoor(false);
            frontDoor.addObserver(homeControllerBuilder.messageObserver);
            return  frontDoor;
        });

        this.heatingSystem = Objects.requireNonNullElseGet(homeControllerBuilder.heatingSystemAdapter, () -> {
            var legacyHeatingSystem =  new LegacyHeatingSystem(false);
            legacyHeatingSystem.addObserver(homeControllerBuilder.messageObserver);
            return new HeatingSystemAdapter(legacyHeatingSystem);
        });

        this.light = Objects.requireNonNullElseGet(homeControllerBuilder.light, () -> {
            var light = new Light(false);
            light.addObserver(homeControllerBuilder.messageObserver);
            return light;
        });

        this.coffeeMaker = Objects.requireNonNullElseGet(homeControllerBuilder.coffeeMaker, () -> {
            var coffeeMaker = new CoffeeMaker(new StrongCoffeeCreationStrategy());
            coffeeMaker.addObserver(homeControllerBuilder.messageObserver);
            return coffeeMaker;
        });
    }

    public void onMovement() {
        if (alarmSystem.isTurnedOn()) {
            alarmSystem.alarm();
        }

        if(!light.isTurnedOn()) {
            light.turnOn();
        }else{
            System.out.println("[HomeController] nothing to do (light is already turned on)");
        }
    }

    public void onGoingHome() {
        if (!heatingSystem.isTurnedOn()) {
            heatingSystem.turnOn();
        }else{
            System.out.println("[HomeController] nothing to do (heating system is already turned on)");
        }
    }

    public void onArriveHome(){
        if(alarmSystem.isTurnedOn()) {
           alarmSystem.turnOff();
        }else {
            System.out.println("[HomeController] nothing to do (alarm system is already turned off)");
        }

        if (!frontDoor.isOpen()) {
            frontDoor.open();
        }else{
            System.out.println("[HomeController] nothing to do (front door is already opened)");
        }

        coffeeMaker.createCoffee();
    }

    public void onChangeToHoliday() {
        coffeeMaker.setCoffeeCreationStrategy(new WeakCoffeeCreationStrategy());
    }

    public void onChangeToWorkingDay() {
        coffeeMaker.setCoffeeCreationStrategy(new StrongCoffeeCreationStrategy());
    }

    public static class HomeControllerBuilder {
        private final Observer messageObserver;
        private AlarmSystem alarmSystem;
        private FrontDoor frontDoor;
        private HeatingSystemAdapter heatingSystemAdapter;
        private Light light;
        private CoffeeMaker coffeeMaker;

        public HomeControllerBuilder(Observer messageObserver) {
            this.messageObserver = messageObserver;
        }

        public HomeControllerBuilder alarmSystem(AlarmSystem alarmSystem) {
            alarmSystem.addObserver(messageObserver);
            this.alarmSystem = alarmSystem;
            return this;
        }

        public HomeControllerBuilder frontDoor(FrontDoor frontDoor) {
            frontDoor.addObserver(messageObserver);
            this.frontDoor = frontDoor;
            return this;
        }

        public HomeControllerBuilder heatingSystemAdapter(HeatingSystemAdapter heatingSystemAdapter) {
            this.heatingSystemAdapter = heatingSystemAdapter;
            return this;
        }

        public HomeControllerBuilder light(Light light) {
            light.addObserver(messageObserver);
            this.light = light;
            return this;
        }

        public HomeControllerBuilder coffeeMaker(CoffeeMaker coffeeMaker) {
            coffeeMaker.addObserver(messageObserver);
            this.coffeeMaker = coffeeMaker;
            return this;
        }

        public HomeController build() {
            return new HomeController(this);
        }
    }
}
