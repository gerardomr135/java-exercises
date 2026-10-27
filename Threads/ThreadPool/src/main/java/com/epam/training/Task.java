package com.epam.training;

import java.util.TimerTask;

class Task extends TimerTask {
    int x;
    public void run() {
        System.out.print(++x);
    }
}