package com.epam.training;

import com.epam.training.VolatileDemo.VolatileDemo;

/**
 * Hello world!
 *
 */
public class App
{
    private volatile static boolean flag;
    public static void main( String[] args ) throws InterruptedException {
        new Thread(() -> { while (!!!flag) {} }).start();
        Thread.sleep(50);
        flag = true;
        System.out.println("ABC");
//        VolatileDemo.ChangeListener c = new VolatileDemo.ChangeListener();
//        VolatileDemo.ChangeMaker m = new VolatileDemo.ChangeMaker();
//        c.start();
//        m.start();
//
//        try{
//            Thread.sleep(500);
//        }catch (InterruptedException e) {
//            throw new IllegalStateException();
//        }
//        c.interrupt();
//        m.interrupt();
    }
}
