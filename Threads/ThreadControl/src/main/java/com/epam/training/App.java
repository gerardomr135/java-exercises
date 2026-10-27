package com.epam.training;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


/**
 * Hello world!
 *
 */
public class App extends Thread
{

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        }catch (InterruptedException e){}
        System.out.println("Run");
    }

    public static void main( String[] args ) throws InterruptedException {
        Thread t1 = new App();

        t1.start();
        System.out.println("Start ");
        t1.sleep(10);
        System.out.println("Interrupt ");
        t1.interrupt();
        System.out.println("End ");
        //Thread.yield();

        //new Thread(new MyThread()).start();
        //new Thread(new MyThread()).start();

//        System.out.println("start of " + Thread.currentThread().getName());
//        new Thread(() -> {
//            System.out.println("start 1");
//            try {
//                TimeUnit.MILLISECONDS.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("end 1");
//        }).start();
//        JoinThread thread = new JoinThread();
//        thread.start();
//        try {
//            thread.join(100);
//            // or TimeUnit.MILLISECONDS.timedJoin(thread, 100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("end of " + Thread.currentThread().getName());


//            new Thread(() -> {
//                System.out.println("start 1");
//                Thread.yield();
//                System.out.println("end 1");
//            }).start();
//            new Thread(() -> {
//                System.out.println("start 2");
//                System.out.println("end 2");
//            }).start();

//        var t = new Thread(new ThreadState());
//        System.out.println(t.getName() + " is " + t.getState());
//        t.start();
//        t.join();
//        t.wait();
//        t.setPriority(Thread.MAX_PRIORITY);
//        System.out.println(t.getName() + " is " + t.getState());

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

//        new ThreadException().start();
//        try {
//            TimeUnit.MILLISECONDS.sleep(20);
//        } catch (InterruptedException e) {
//            throw new IllegalStateException();
//        }
//        System.out.println("end of main thread");

//        ThreadInterrupt thread = new ThreadInterrupt();
//        thread.start();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new IllegalStateException();
//        }
//        thread.interrupt();
    }
}
