package com.epam.rd.autotask.thread;

public class Demo {
    public static void main(String[] args) {
        // place your code here
        EnterKeyInputStream in = new EnterKeyInputStream(1000);
        System.setIn(in);

        Thread t = new Thread(Spam::send);
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.setIn(System.in);
    }
}
