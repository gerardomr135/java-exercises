package com.fireal99.Interfaces;

public interface Electronic {
    // Constant variable
    String LED = "LED";

    // Abstract method
    void getElectricityUse();

    // static method
    static boolean isEnergyEfficient(String electronicType) {
        return electronicType.equals(LED);
    }

    // Default method
    default void printDescription() {
        System.out.println("Electronic Description");
    }

    void conflict();
    default void defaultConflict() {
    }
}
