package com.fireal99;

import java.util.Timer;
import java.util.concurrent.*;

/**
 * Hello world!
 *
 */
public class App 
{

    public static class MyThread extends Thread
    {
        public void run(int a)
        {
            for(int i=0; i<10; i++)
            {
                System.out.println(i);
            }
        }
    }

    static class CustomRunnable extends Thread {
        private final long delay;
        public CustomRunnable(long delay) {
            this.delay = delay;
        }

        public void run() {
            try {
                //Thread.sleep(delay);
                TimeUnit.SECONDS.sleep(delay);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    static class PriorityThread extends Thread {
        public void run() {
            for (int i = 0; i < 4; i++)
                System.out.println("Thread: " + getName() + " i=" + i);
        }
    }

    static class MyThreadInterrupted extends Thread {
        public void run() {
            int i = 1;
            while( !isInterrupted() )  {
                System.out.println("Thread:" + getName()+ " i=" + i++);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    public static void main( String[] args ) throws InterruptedException, ExecutionException {
//        Thread t1 = new CustomRunnable(3);
//        t1.start();
//        t1.join();
//        System.out.println("main thread ending...");
//        new Thread(() -> {
//            System.out.println("start 1");
//            Thread.yield();
//            System.out.println("end 1");
//        }).start();
//        new Thread(() -> {
//            System.out.println("start 2");
//            System.out.println("end 2");
//        }).start();
//        PriorityThread minPriorityThread = new PriorityThread ();
//        minPriorityThread.setName("Thread Min");
//        minPriorityThread.setPriority(Thread.MIN_PRIORITY);
//        PriorityThread maxPriorityThread = new PriorityThread ();
//        maxPriorityThread.setName("Thread Max");
//        maxPriorityThread.setPriority(Thread.MAX_PRIORITY);
//        PriorityThread normalPriorityThread = new PriorityThread ();
//        normalPriorityThread.setName("Thread Norm");
//        normalPriorityThread.setPriority(Thread.NORM_PRIORITY);
//        minPriorityThread.start();
//        normalPriorityThread.start();
//        maxPriorityThread.start();
//        new Thread(() -> {
//            if (Boolean.TRUE) {
//                throw new RuntimeException();
//            }
//            System.out.println("end of Thread");
//        }).start();
//        try {
//            TimeUnit.MILLISECONDS.sleep(20);
//        } catch (InterruptedException e) {
//            throw new IllegalStateException();
//        }
//        System.out.println("end of main thread");
//        MyThreadInterrupted thread = new MyThreadInterrupted();
//        thread.start();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new IllegalStateException();
//        }
//        thread.interrupt();
//        Runnable r = () -> {
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {}
//        System.out.print("Run ");
//        };
//        Thread th = new Thread(r);
//        th.start();
//        System.out.print("Start ");
//        Thread.sleep(10);
//        System.out.print("Interrupt ");
//        Thread.interrupted();
//        System.out.print("End ");
//        Runnable r = () -> System.out.print("Run");
//        Callable<Integer> call = () -> 77;                              // line 1
//        ExecutorService service = Executors.newSingleThreadExecutor();
//        service.submit(r);                                              // line 2
//        Future<Integer> future = service.submit(call);                  // line 3
//        System.out.print(future.get());
//        service.shutdown();
//
//        Timer timer = new Timer();
//        timer.schedule(null, 50);
//        timer.purge();
//        timer.cancel();
//        BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);
//        new Thread(() -> {
//            for (int i = 0; i < 3; i++) {
//                try {
//                    TimeUnit.MILLISECONDS.sleep(1);
//                    queue.put("Java" + i);
//                    System.out.println("Element " + i + " added");
//                } catch(InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//        new Thread(() -> {
//            try {
//                System.out.println("Element " + queue.take() + " took");
//            } catch(InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }

//    BlockingQueue<String> strings = new ArrayBlockingQueue<>(7);
//strings.put("A");
//strings.put("C");
//strings.put("E");
//strings.put("F");
//strings.put("G");
//strings.remove();
//strings.take();
//strings.put("C");
//strings.put("T");
//strings.poll()
//System.out.println(strings);

        ConcurrentHashMap<Integer, String> hashMap = new ConcurrentHashMap<>();
        ExecutorService service = Executors.newFixedThreadPool(3);
        try {
            service.execute(() ->  hashMap.put(1, "C"));
            service.execute(() ->  hashMap.put(2, "D"));
            service.execute(() ->  hashMap.put(3, "D"));
        } finally {
            System.out.println("xD");
            service.shutdown();
        }
        hashMap.putIfAbsent(2, "C");
        hashMap.remove(2);
        hashMap.putIfAbsent(0, "C");
        hashMap.replace(1, "C", "A");
        System.out.println(hashMap);
    }
}
