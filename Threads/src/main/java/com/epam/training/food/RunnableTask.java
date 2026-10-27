package com.epam.training.food;

import java.util.concurrent.Callable;

public class RunnableTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Talking " + Thread.currentThread().getName());
    }
}
