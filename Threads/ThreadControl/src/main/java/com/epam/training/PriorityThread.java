package com.epam.training;

public class PriorityThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 4; i++)
            System.out.println("Thread: " + getName() + " i=" + i);
    }
}
