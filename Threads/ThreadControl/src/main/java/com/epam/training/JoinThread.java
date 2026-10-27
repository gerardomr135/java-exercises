package com.epam.training;

import java.util.concurrent.TimeUnit;

public class JoinThread extends Thread {
    public void run() {
        System.out.println("START");
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new IllegalStateException();
        }
        System.out.println("END");
    }
}
