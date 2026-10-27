package com.epam.training.smarthome.domain.devices.heatingsystem;

import com.epam.training.smarthome.domain.observer.Observable;

public class LegacyHeatingSystem extends Observable  {
    private boolean isTurnedOn;

    public LegacyHeatingSystem(boolean isTurnedOn) {
        this.isTurnedOn = isTurnedOn;
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    public void operate(boolean isTurnedOn) {
        if (isTurnedOn) {
            notifyObservers("[HeatingSystem] turn on");
            this.isTurnedOn = true;
            return;
        }

        notifyObservers("[HeatingSystem] turn off");
        this.isTurnedOn = false;
    }
}
