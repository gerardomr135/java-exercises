package com.epam.training;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ExecutionException, InterruptedException {
        new Thread(new TaskTime()).start();
        new Thread(new TaskTime()).start();
        //        Timer timer = new Timer();
//        timer.schedule(new TaskTime(), 25, 50);
//        timer.schedule(new TaskTime(), 25, 50);
//        Thread.sleep(100);
//        timer.cancel();
        //        Timer timer = new Timer();
//        timer.schedule(new TaskTime(), 25, 50);
//        timer.purge();
//        timer.cancel();
        //        Thread t = new DealThread();
//        ExecutorService service = Executors.newSingleThreadExecutor();
//        service.submit(t);
//        service.submit(t);
//        service.shutdown();
        //        Runnable r = () -> System.out.print("Run");
//        Callable<Integer> call = () -> 77;                              // line 1
//        ExecutorService service = Executors.newSingleThreadExecutor();
//        service.submit(r);                                              // line 2
//        Future<Integer> future = service.submit(call);                  // line 3
//        System.out.print(future.get());
//        service.shutdown();
//        StringBuffer sb = new StringBuffer();
//        Runnable r = () -> sb.append("A");
//        ExecutorService service = Executors.newFixedThreadPool(2);
//        for (int i = 0; i < 2; i++) {
//            Future <StringBuffer> f = service.submit(r, sb);
//            StringBuffer result = f.get();
//            System.out.print(result);
//        }
//        service.shutdown();
//        Timer timer = new Timer();
//        timer.schedule(new Task(), 25, 50);
//        Thread.sleep(100);
//        timer.cancel();
//        final int NUM_OF_WORKS = 20;
//        try (ExecutorService pool = Executors.newFixedThreadPool(4)) {
//            TestCallable[] works = new TestCallable[NUM_OF_WORKS];
//            Future<String>[] futures = new Future[NUM_OF_WORKS];
//            for (int i = 0; i < NUM_OF_WORKS; i++) {
//                works[i] = new TestCallable(i + 1);
//                futures[i] = pool.submit(works[i]);
//            }
//            for (int i = 0; i < NUM_OF_WORKS; ++i) {
//                try {
//                    System.out.println(futures[i].get() + " ended");
//                } catch (InterruptedException | ExecutionException ex) {
//                    throw new IllegalStateException();
//                }
//            }
//            pool.shutdown();
//        }
    }

    private static Thread getWaiter() {
        var n = 3;
        List<Thread> threads = getThreads(n);


        var waiter = new Thread(()-> {
           // wait for x, y and z
            for(var i = 0; i < n; i++) {
                if (threads.get(i).isAlive()) {
                    try {
                        threads.get(i).wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        waiter.start();

        threads.forEach(Thread::interrupt);

        return waiter;
    }

    private static List<Thread> getThreads(int n) {
        List<Thread> threads = new ArrayList<Thread>(n);
        for (int i = 0; i < n; i++) {
            var t = new Thread(()->{
                System.out.println("Working: "+Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted: " + Thread.currentThread().getName());
                }
            });
            t.start();
            threads.add(t);
        }
        return threads;
    }
}
