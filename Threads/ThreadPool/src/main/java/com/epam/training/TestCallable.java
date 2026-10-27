package com.epam.training;

import java.util.concurrent.Callable;

class TestCallable implements Callable<String> {
    private final int workerNumber;
    TestCallable(int workerNumber) {
        this.workerNumber = workerNumber;
    }
    public String call() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Work" + workerNumber + ": " + i);
            try {
                Thread.sleep((int)(Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new IllegalStateException();
            }
        }
        return "Worker " + workerNumber;
    }
}