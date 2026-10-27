package com.epam.training;

public class    ThreadState extends Thread {
    public void run(){
        System.out.println(getName()+" is " + getState());
    }
}
