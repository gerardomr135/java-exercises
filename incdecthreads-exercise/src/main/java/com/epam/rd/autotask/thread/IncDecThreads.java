package com.epam.rd.autotask.thread;

public class IncDecThreads {

    static final int COUNT = 5000;
    static long value;

    /**
     * In a loop increments {@code COUNT} times the {@code value}
     * and prints to the console the name of the class, the name of
     * the thread and the value of the field {@code value}.
     */
    static class Increment extends Thread {
        // TODO place your code here
        @Override
        public void run() {
            for (int i = 1; i <= COUNT; i++) {
                value++;
                System.out.println(
                        Increment.class.getSimpleName() + " : "
                                + Thread.currentThread().getName() + " : "
                                + value
                );
            }
        }
    }

    /**
     * In a loop decrements {@code COUNT} times the {@code value}
     * and prints to the console the name of the class, the name of
     * the thread and the value of the field {@code value}.
     */
    static class Decrement implements Runnable {
        // TODO place your code here
        @Override
        public void run() {
            for (int i = 1; i <= COUNT; i++) {
                value--;
                System.out.println(
                        Increment.class.getSimpleName() + " : "
                                + Thread.currentThread().getName() + " : "
                                + value
                );
            }
        }
    }

    public static void main(String[] args) {
        // TODO place your code here
        Thread incrementThread = new Thread(new Increment());
        Thread decrementThread = new Thread(new Decrement());

        incrementThread.start();
        decrementThread.start();
    }
}
