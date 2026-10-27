package com.epam.training.VolatileDemo;

public class VolatileDemo {
    private static volatile int varVolat = 0;
    private static int varNonVolat = 0;

    public static class ChangeMaker extends Thread {
        @Override
        public void run() {
            int localValue = 0;
            while (!isInterrupted()) {
                varVolat = varNonVolat = ++localValue;
            }
        }
    }

    public static class ChangeListener extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                if (varVolat != varNonVolat) {
                    System.out.println("Error: " + varVolat + " != " + varNonVolat);
                }
            }
        }
    }
}
