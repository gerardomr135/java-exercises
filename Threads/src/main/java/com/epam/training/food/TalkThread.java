package com.epam.training.food;

public class TalkThread extends Thread {
    public void run(){
        for( int i=0; i<5; i++ ){
            System.out.print(this.getName() + ": ");
            System.out.println("Talking " + (i+1));
        }
    }
}
