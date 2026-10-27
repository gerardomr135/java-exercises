package com.epam.training.food;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App extends Thread
{
    public void run(Thread t) {
        System.out.println("R");
    }

    public static void main( String[] args ) throws Exception
    {
        //new Thread(new App()).start();

        Runnable r = new RunnableTask();
        var t = new Thread(r);
        t.start();
//        List<Integer> listInt = List.of(1, 10, 100, 1_000, 10_000);
//        int sum1 = listInt.stream().mapToInt(x -> x).sum();
//// or
//        int sum2 = listInt.stream().reduce(0, Integer::sum);
//        System.out.println(sum1 + " " + sum2);
//output: 11111 11111
//        System.out.println("main start");
//        TalkThread talk = new TalkThread();
//        talk.start();
//        System.out.println("main ended!");
    }
}
