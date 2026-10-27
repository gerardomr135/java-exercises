package com.epam.training;

import java.util.TimerTask;

class TaskTime extends TimerTask {
    int x;
    public void run() {
        System.out.print(++x);
    }
}