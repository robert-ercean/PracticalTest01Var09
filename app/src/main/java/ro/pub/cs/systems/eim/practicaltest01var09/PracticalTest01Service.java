package ro.pub.cs.systems.eim.practicaltest01var09;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class PracticalTest01Service extends Service {

    private ProcessingThread processingThread;

    public PracticalTest01Service() {
    }

    @Override
    public int onStartCommand(Intent intent,
                              int flags,
                              int startId) {
        Bundle data = intent.getExtras();
        String sum = data.getString("broadcast");
        processingThread = new ProcessingThread(this, sum);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}