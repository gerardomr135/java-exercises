package com.epam.training.smarthome.domain.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    protected void notifyObservers(String message) {
        observers.forEach((observer) -> observer.update(message));
    }
}
