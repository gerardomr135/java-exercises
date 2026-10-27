package com.epam.rd.autocode.threads.maxbystreams;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class MaxOfMatrix {

    /**
     * Pauses current thread or{@code pause} millis
     *
     * @param pause time in millis
     */
    private static void pause(int pause) {
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static String oneThreadSearch(int[][] matrix, int pause) {
        long start = System.currentTimeMillis();

        return String.format("%d %d",
                java.util.Arrays.stream(matrix)
                        .flatMapToInt(java.util.Arrays::stream)
                        .reduce(Integer.MIN_VALUE, (currentMax, nextNumber) -> {
                            pause(pause);
                            return Math.max(currentMax, nextNumber);
                        }),
                System.currentTimeMillis() - start);
    }

    public static String multipleThreadSearch(int[][] matrix, int pause) {
        long start = System.currentTimeMillis();

        return String.format("%d %d",
                // java.util.concurrent.ForkJoinPool.commonPool().submit(() -> {
                java.util.Arrays.stream(matrix)
                        .parallel() // Parallelize the rows
                        .mapToInt(row -> java.util.Arrays.stream(row)
                                .parallel() // Parallelize within each row for maximum speed
                                .reduce(Integer.MIN_VALUE, (currentMax, nextNumber) -> {
                                    pause(pause);
                                    return Math.max(currentMax, nextNumber);
                                }))
                        .reduce(Integer.MIN_VALUE, (maxOfRow, nextMaxOfRow) -> {
                            pause(pause);
                            return Math.max(maxOfRow, nextMaxOfRow);
                        }),
                // }).join(),
                System.currentTimeMillis() - start);
    }
}
