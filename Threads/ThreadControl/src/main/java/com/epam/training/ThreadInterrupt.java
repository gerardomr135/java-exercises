package com.epam.training;

public class ThreadInterrupt extends Thread {
    @Override
    public void run() {
        int i = 1;
        while( !isInterrupted() )  {
            System.out.println("Thread:" + getName()+ " i=" + i++);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
