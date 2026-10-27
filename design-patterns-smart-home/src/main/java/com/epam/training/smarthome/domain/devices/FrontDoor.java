package com.epam.training.smarthome.domain.devices;

import com.epam.training.smarthome.domain.observer.Observable;

public class FrontDoor extends Observable {
    private boolean isOpen;

    public FrontDoor(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void open() {
        notifyObservers("[FrontDoor] open");
        isOpen = true;
    }

    public void close() {
        notifyObservers("[FrontDoor] close");
        isOpen = false;
    }
}
