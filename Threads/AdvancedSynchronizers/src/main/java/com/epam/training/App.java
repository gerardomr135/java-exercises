package com.epam.training;

import com.epam.training.CountDownLatch.AuctionLatch;
import com.epam.training.CountDownLatch.ParticipantLatch;
import com.epam.training.CyclicBarrier.Auction;
import com.epam.training.CyclicBarrier.Participant;
import com.epam.training.Semaphore.ArraySort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Hello world!
 *
 */
public class App 
{
    static int y;
    static CyclicBarrier barrier = new CyclicBarrier(2);
    public static void main(String[] args) {
        System.out.println(new Random().ints(1000, 100, 1000)
                .boxed()
                .parallel()
                .sorted((o1, o2) -> o2.intValue() - o1.intValue())
                .toList());
//        int x = ++y;
//        Thread th = new Thread(() -> {
//            try {
//                y++;
//                barrier.await();
//                System.out.print(x + " " + y + " ");
//            } catch (InterruptedException | BrokenBarrierException ex) {
//                ex.printStackTrace();
//            }
//        });
//        ExecutorService s = Executors.newFixedThreadPool(2);
//        for (int i = 0; i < 2; i++) {
//            s.submit(th);
//        }
//        s.shutdown();
    }
//    static CyclicBarrier barrier = new CyclicBarrier(2);
//    public static void main(String[] args) {
//        int x[] = new int[2];
//        Thread th = new Thread(() -> {
//            try {
//                x[0] = x[1]++;
//                barrier.await();
//                System.out.print(x[0] + " " + x[1] + " ");
//            } catch (InterruptedException | BrokenBarrierException ex) {
//                ex.printStackTrace();
//            }
//        });
//        ExecutorService s = Executors.newFixedThreadPool(2);
//        for (int i = 0; i < 2; i++) {
//            s.submit(th);
//        }
//        s.shutdown();
//    }
////    static CyclicBarrier barrier = new CyclicBarrier(4);
//    static int x;
//    public static void main(String[] args) {
//        Thread th = new Thread(() -> {
//            try {
//                ++x;
//                barrier.await();
//                System.out.print(x);
//            } catch (InterruptedException | BrokenBarrierException ex) {
//                ex.printStackTrace();
//            }
//        });
//        ExecutorService s = Executors.newFixedThreadPool(4);
//        for (int i = 0; i < 4; i++) {
//            s.submit(th);
//        }
//        s.shutdown();
//    }
    // 0 1
    // 1 1
//    public static final int ITEMS_COUNT = 10;
//    public final static Semaphore sortSemaphore = new Semaphore(0, true);
//    public static void main( String[] args )
//    {
//        List<String> list = new ArrayList<>();
//        AtomicInteger atomic = new AtomicInteger(1);
//        CyclicBarrier barrier = new CyclicBarrier(2, () -> System.out.print(list));
//        Runnable r = () -> {
//            try {
//                Thread.sleep(100 * atomic.incrementAndGet());
//                list.add("a");
//                barrier.await();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        };
//        new Thread(r).start();
//        new Thread(r).start();
//        new Thread(r).start();
//        new Thread(r).start();
////        int numbersParticipant = 5;
////        AuctionLatch auction = new AuctionLatch(numbersParticipant);
////        int startPrice = 110;
////        auction.start();
////        for (int num = 0; num < numbersParticipant; num++) {
////            int cash = 100 + new Random().nextInt(20);
////            ParticipantLatch participant = new ParticipantLatch(num, startPrice, cash, auction);
////            auction.add(participant);
////            participant.start();
////        }
////        int numberParticipant = 5;
////        Auction auction = new Auction(numberParticipant);
////        int startPrice = 50;
////        System.out.println("Start price : " + startPrice);
////        for (int num = 0; num < numberParticipant; num++) {
////            int cash = 100 + new Random().nextInt(50);
////            Participant participant = new Participant(num, startPrice, cash, auction);
////            auction.add(participant);
////            participant.start();
////        }
//
////        int[] items = new int[ITEMS_COUNT];
////        for (int i = 0; i < items.length; i++) {
////            items[i] = (int) (Math.random()*10);
////        }
////        System.out.println("Initial array: " + Arrays.toString(items));
////        new ArraySort(items).start();
////        for (int i = 0; i < items.length; i++) {
////            sortSemaphore.acquireUninterruptibly();
////            System.out.println("Step [" + (i + 1) + "]: " + Arrays.toString(items));
////        }
////        System.out.println("Sorted array: " + Arrays.toString(items));
//    }
}
