package com.epam.rd.autotask.threads.batchprocessor;

import java.util.concurrent.BlockingQueue;
import java.util.function.UnaryOperator;

public class Transformer extends Thread {
    // place your code here
    private final UnaryOperator<String> op;
    private final BlockingQueue<String> sourceQueue;
    private final BlockingQueue<String> destinationQueue;
    private static final String EMPTY_MESSAGE = "";

    public Transformer(UnaryOperator<String> op,
            BlockingQueue<String> sourceQueue,
            BlockingQueue<String> destinationQueue) {
        // place your code here
        if (op == null || sourceQueue == null || destinationQueue == null) {
            throw new NullPointerException();
        }

        this.op = op;
        this.sourceQueue = sourceQueue;
        this.destinationQueue = destinationQueue;
    }

    @Override
    public void run() {
        // place your code here
        while (true) {
            try {
                String msg = sourceQueue.take();
                if (msg.equals(EMPTY_MESSAGE)) {
                    destinationQueue.put(EMPTY_MESSAGE);
                    return;
                }

                msg = op.apply(msg);

                destinationQueue.put(msg);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
