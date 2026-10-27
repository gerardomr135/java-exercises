package com.epam.training.SynchronousQueue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class Producer implements Runnable {
    private final SynchronousQueue<String> drop;
    List<String> messages = Arrays.asList( "Mares eat oats", "Does eat oats",
            "Little lambs eat ivy",
            "Wouldn't you eat ivy too?");
    public Producer(SynchronousQueue<String> drop) {
        this.drop = drop;
    }
    public void run() {
        try {
            for (String str : messages) {
                drop.put(str);
            }
            drop.put("DONE");
        } catch(InterruptedException intEx) {
            System.out.println("Interrupted! Last one out, turn out the lights!");
        }
    }
}
