package android.com.myqq.service;

/**
 * Created by Administrator on 2017/5/3.
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

public class CountService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {


                    try {
                        Thread.sleep(1000);
                        boolean netState = getNetState();
                        //发送广播
                        Intent intent = new Intent();
                        intent.putExtra("netState", netState);
                        intent.setAction("android.com.myqq.receiver.MyReceiver");
                        sendBroadcast(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }


                }


        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean getNetState(){
        ConnectivityManager connectionManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }


}

