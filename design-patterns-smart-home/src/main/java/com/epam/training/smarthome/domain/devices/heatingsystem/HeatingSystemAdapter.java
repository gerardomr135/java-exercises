package com.epam.training.smarthome.domain.devices.heatingsystem;

public class HeatingSystemAdapter implements HeatingSystem {
    private final LegacyHeatingSystem legacyHeatingSystem;

    public HeatingSystemAdapter(LegacyHeatingSystem legacyHeatingSystem) {
        this.legacyHeatingSystem = legacyHeatingSystem;
    }

    @Override
    public void turnOn() {
        if(legacyHeatingSystem.isTurnedOn()) {
            return;
        }

        legacyHeatingSystem.operate(true);
    }

    @Override
    public void turnOff() {
        if (!isTurnedOn()) {
            return;
        }

        legacyHeatingSystem.operate(false);
    }

    @Override
    public boolean isTurnedOn() {
        return legacyHeatingSystem.isTurnedOn();
    }
}
