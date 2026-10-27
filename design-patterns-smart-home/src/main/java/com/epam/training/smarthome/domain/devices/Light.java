package com.epam.training.smarthome.domain.devices;

import com.epam.training.smarthome.domain.observer.Observable;

public class Light extends Observable {
    private boolean isTurnedOn ;

    public Light(boolean isTurnedOn) {
        this.isTurnedOn = isTurnedOn;
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    public void turnOff() {
        notifyObservers("[Light] turn off");
        isTurnedOn = false;
    }

    public void turnOn() {
        notifyObservers("[Light] turn on");
        isTurnedOn = true;
    }
}
