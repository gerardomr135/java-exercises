package com.epam.training.SumOfNumberUsingForkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class SumOfNumberUsingForkJoin {
    private final static long N = 1_000_000L;
    private static final int NUM_THREADS = 10;
    public static void main(String[] args) {
        Integer i1 = IntStream.iterate(1, i -> ++i)
                .limit(5)
                .findAny().getAsInt();

        Integer i2 = IntStream.iterate(6, i -> ++i)
                .limit(5)
                .parallel()
                .sorted()
                .findAny().getAsInt();
        System.out.println(i1 + " " + i2);
//        ForkJoinPool pool = new ForkJoinPool(NUM_THREADS);
//        long computedSum = pool.invoke(new RecursiveSumOfNumber(0, N));
//        long formulaSum = (N * (N + 1)) / 2;
//        System.out.printf("Sum for range 1..%d; computed sum = %d, formula sum = %d%n", N, computedSum, formulaSum);
    }

    static class RecursiveSumOfNumber extends RecursiveTask<Long> {
        private final long from;
        private final long to;
        public RecursiveSumOfNumber(long from, long to) {
            this.from = from;
            this.to = to;
        }
        public Long compute() {
            if ((to - from) <= N / NUM_THREADS) {
                long localSum = 0;
                for (long number = from; number <= to; number++) {
                    localSum += number;
                }
                System.out.printf("\tSum of range %d to %d is %d%n", from, to, localSum);
                return localSum;
            } else {
                long middle = (from + to) / 2;
                System.out.printf("Forking into two ranges: %d to %d and %d to %d%n", from, middle, middle + 1, to);
                RecursiveSumOfNumber firstHalf = new RecursiveSumOfNumber(from, middle);
                firstHalf.fork();
                RecursiveSumOfNumber secondHalf = new RecursiveSumOfNumber(middle + 1, to);
                long resultSecondTask = secondHalf.compute();
                return firstHalf.join() + resultSecondTask;
            }
        }
    }
}
