package com.epam.training.smarthome.domain.devices;

import com.epam.training.smarthome.domain.observer.Observable;

public class AlarmSystem extends Observable {
    private boolean isTurnedOn;

    public AlarmSystem(boolean isTurnedOn) {
        this.isTurnedOn = isTurnedOn;
    }

    public void alarm() {
        notifyObservers("[AlarmSystem] alarm");
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    public void turnOff() {
       notifyObservers("[AlarmSystem] turn off");
       isTurnedOn = false;
    }

    public void turnOn() {
        notifyObservers("[AlarmSystem] turn on");
        isTurnedOn = true;
    }
}
