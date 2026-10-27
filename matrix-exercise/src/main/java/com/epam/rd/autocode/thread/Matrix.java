package com.epam.rd.autocode.thread;

import java.util.*;
import java.util.concurrent.*;

public class Matrix {

	private static final int MIN_VALUE = 0;

	private static final int MAX_VALUE = 1000;

	public static int[][] matrixGenerator(int m, int n) {
		int[][] res = new int[m][n];
		Random r = new Random();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				res[i][j] = r.nextInt(MAX_VALUE - MIN_VALUE) + MIN_VALUE;
			}
		}
		return res;
	}

	public static String oneThreadSearch(int[][] ar, int pause) throws InterruptedException {
		int maxElement = 0;

		long startTime = System.currentTimeMillis();
        for (int[] ints : ar) {
            maxElement = maxElement(ints, pause);
        }
		long endTime = System.currentTimeMillis();

		long duration = endTime - startTime;

		return maxElement + " " + duration;
	}

	public static int maxElement(int[] ar, int pause) throws InterruptedException {
		int maxElement = Integer.MIN_VALUE;

		for (int anInt : ar) {
			Thread.sleep(pause);

			if (anInt > MAX_VALUE) {
				maxElement = anInt;
			}
		}

		return maxElement;
	}

	static class Worker implements Callable<Integer> {
		private final int[] row;
		private final int pause;

		public Worker(int[] row, int pause) {
			this.row = row;
			this.pause = pause;
		}

		@Override
		public Integer call() throws Exception {
			return maxElement(row, pause);
		}
	}

	public static String multipleThreadSearch(int[][] ar, int pause) throws InterruptedException, ExecutionException {
        List<Future<Integer>> futures = new ArrayList<>(ar.length);
		ExecutorService executor = Executors.newFixedThreadPool(ar.length);

		long startTime = System.currentTimeMillis();

		for (int[] ints : ar) {
			Future<Integer> future =  executor.submit(new Worker(ints, pause));
			futures.add(future);
		}


		int maxElement = Integer.MIN_VALUE;
        for (Future<Integer> future : futures) {
			maxElement = future.get();
		}

		long endTime = System.currentTimeMillis();

		long duration = endTime - startTime;

		executor.shutdown();

		return maxElement + " " + duration;
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int[][] ar = matrixGenerator(4, 50);

		System.out.println(oneThreadSearch(ar, 1));
		System.out.println(multipleThreadSearch(ar, 1));
	}

}
