package com.epam.training.SynchronousQueue;

import java.util.concurrent.SynchronousQueue;

public class Consumer implements Runnable {
    private SynchronousQueue<String> drop;
    public Consumer(SynchronousQueue<String> drop) {
        this.drop = drop;
    }
    public void run() {
        try {
            String msg = null;
            while (!((msg = drop.take()).equals("DONE"))) {
                System.out.println(msg);
            }
        } catch(InterruptedException intEx) {
            System.out.println("Interrupted! Last one out, turn out the lights!");
        }
    }
}
