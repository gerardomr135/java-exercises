package com.epam.rd.autotask.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Runs threads, each of which prints its own message
 * with a given time interval while the thread is not interrupted
 */
public class Spam {
    // place your code here
    private final String[] messages;
    private final long[] intervals;
    private final List<Thread> threadsPool;

    public Spam(String[] messages, long[] intervals) throws IllegalArgumentException {
        // place your code here
        validateMessagesAndIntervals(messages, intervals);

        this.messages = messages;
        this.intervals = intervals;
        this.threadsPool = new ArrayList<>(messages.length);
    }

    private void validateMessagesAndIntervals(String[] messages, long[] intervals) throws IllegalArgumentException {
        if (messages == null || intervals == null) {
            throw new IllegalArgumentException("Messages and interval arrays must not be null");
        }

        if (messages.length != intervals.length) {
            throw new IllegalArgumentException("Messages and interval arrays must have same length");
        }

        if (messages.length < 2) {
            throw new IllegalArgumentException("Messages and interval arrays must have length more than 2");
        }
    }

    /**
     * Runs all threads in the pool
     */
    public void start() {
        // place your code here
        for (int i = 0; i < messages.length; i++) {
            Thread t = new Thread(new Worker(messages[i], intervals[i]));
            t.start();
            threadsPool.add(t);
        }
    }

    /**
     * Interrupts all threads in the pool
     */
    public void stop() {
        // place your code here
        // send interrupt signals
        for (Thread t : threadsPool) {
            if (t.isAlive()) {
                t.interrupt();
            }
        }

        // Waits for the threads to terminate.
        for (Thread t : threadsPool) {
            if (t.isAlive()) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    continue;
                }
            }
        }
    }

    /**
     * This class is used to spam a message with a given time interval.
     * The message must be printed to the console until the thread is interrupted.
     */
    static class Worker implements Runnable {
        // place your code here
        private final String message;
        private final long interval;

        public Worker(String message, long interval) {
            // place your code here
            this.message = message;
            this.interval = interval;
        }

        @Override
        public void run() {
            // place your code here
            while (true) {
                System.out.println(message);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    /**
     * 1) Creates a Spam object.<br/>
     * 2) Starts the threads.<br/>
     * 3) Reads Enter from System.in<br/>
     * 4) Interrupts the threads.<br/>
     */
    public static void send() {
        // place your code here
        String[] messages = new String[] {
                "bbbbbbb",
                "@@@",
                "AAA",
                "xyz",
                "qwerty",
                "abc",
                "####",
                "!@#$%"
        };
        long[] intervals = new long[] {
                100,
                50,
                60,
                80,
                30,
                40,
                70,
                80
        };

        Spam spam = new Spam(messages, intervals);
        spam.start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        spam.stop();
        scanner.close();
    }
}
