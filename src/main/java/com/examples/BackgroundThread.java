package com.examples;

import java.util.TimerTask;

public class BackgroundThread extends TimerTask {

    MySourceTask task = null;

    public BackgroundThread(MySourceTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        task.turnOnRenewFlag();
    }

}
