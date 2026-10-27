package com.epam.rd.autotask.threads.batchprocessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.UnaryOperator;

/**
 *
 */
public class MessagePreprocessor {

    // place your code here
    private boolean completion;
    private final List<BlockingQueue<String>> queues;
    private List<Thread> threads;
    private final List<UnaryOperator<String>> functions;
    private final Collection<String> initial;

    public MessagePreprocessor(Collection<String> initial, List<UnaryOperator<String>> functions) {
        // place your code here
        validateConstructorParameters(initial, functions);

        this.initial = initial;
        this.completion = false;
        this.functions = functions;

        int n = functions.size();
        queues = new ArrayList<>(n + 1);
        queues.add(new ArrayBlockingQueue<>(initial.size()));

        for (int i = 1; i < n; i++) {
            queues.add(i, new ArrayBlockingQueue<>(initial.size() / 2));
        }

        queues.add(new ArrayBlockingQueue<>(initial.size()));

        for (String s : initial) {
            queues.get(0).add(s);
        }
    }

    private void validateConstructorParameters(Collection<String> initial, List<UnaryOperator<String>> functions) {
        if (initial == null || functions == null) {
            throw new NullPointerException("initial and functions must not be null");
        }

        if (initial.isEmpty() || functions.isEmpty()) {
            throw new IllegalArgumentException("initial and functions must not be empty");
        }
    }

    List<BlockingQueue<String>> getState() {
        // place your code here
        return queues;
    }

    /**
     * Starts all operations in separate threads.
     */
    public void start() {
        // place your code here
        threads = new ArrayList<>();
        for (int i = 1; i < queues.size(); i++) {
            Thread t = new Thread(new Transformer(functions.get(i - 1), queues.get(i - 1), queues.get(i)));
            threads.add(t);
            t.start();
        }
    }

    /**
     * @return Optional.empty() if not all jobs have been processed
     *         and processor isn't forcibly stopped, otherwise it stops all
     *         running operations and returns a collection of processed jobs
     * @see MessagePreprocessor#stop()
     */
    public Optional<Collection<? extends String>> getResult() {
        // place your code here
        if (completion) {
            return Optional.of(List.copyOf(queues.get(queues.size() - 1)));
        }

        if (queues.get(queues.size() - 1).size() == initial.size()) {
            completion = true;
            return Optional.of(List.copyOf(queues.get(queues.size() - 1)));
        }

        return Optional.empty();
    }

    /**
     * Stops all threads. The threads must interrupt immediately
     * after finishing current processing object.<br>
     * It must ensure that all threads are stopped.
     */
    public void stop() {
        if (threads != null) {
            for (Thread t : threads) {
                t.interrupt();
            }
        }
        completion = true;
    }
}
