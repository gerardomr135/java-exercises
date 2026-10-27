package com.epam.training;

import com.epam.training.BlockingQueue.FileRunTask;
import com.epam.training.BlockingQueue.SearchTask;
import com.epam.training.SynchronousQueue.Consumer;
import com.epam.training.SynchronousQueue.Producer;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Enter directiry -> ");
//        String dir = sc.next();
//        System.out.print("Enter keyWord -> ");
//        String word = sc.next();
//        BlockingQueue<File> que = new ArrayBlockingQueue<>(10);
//        new Thread(new FileRunTask(que, new File(dir))).start();
//        for (int i = 0; i < 50; i++) {
//            new Thread(new SearchTask(que, word)).start();
//        }
        SynchronousQueue<String> drop = new SynchronousQueue<String>();
        new Thread( new Producer(drop)).start();
        new Thread( new Consumer(drop)).start();
    }
}
