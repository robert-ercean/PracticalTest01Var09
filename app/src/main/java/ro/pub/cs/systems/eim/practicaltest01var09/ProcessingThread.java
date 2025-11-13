package ro.pub.cs.systems.eim.practicaltest01var09;

import android.content.Context;
import android.content.Intent;

import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread {
    Context context = null;
    private boolean should_run = true;
    String sum;
    public ProcessingThread(Context context, String sum) {
        this.context = context;
        this.sum = sum;
    }

    @Override
    public void run() {
        while(should_run){
            Intent intent = new Intent("my_intent");
            intent.putExtra("broadcast",
                    new Date(System.currentTimeMillis()) + "sum: " + sum);
            context.sendBroadcast(intent);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stopThread() {
        should_run = false;
    }
}