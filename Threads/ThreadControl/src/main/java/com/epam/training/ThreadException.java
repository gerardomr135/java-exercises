package com.epam.training;

public class ThreadException extends Thread{
    @Override
    public void run() {
        if (Boolean.TRUE) {
            throw new RuntimeException();
        }
        System.out.println("end of Thread");
    }
}
