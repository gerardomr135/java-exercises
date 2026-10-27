package com.epam.training;

public class DealThread extends Thread {
    int i = 1;
    public void run() {
        for (int i = 0; i < 2; i++) {
            System.out.print(i);
        }
    }
}
