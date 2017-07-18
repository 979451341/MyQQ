package android.com.myqq.receiver;

import android.app.Activity;
import android.com.myqq.R;
import android.com.myqq.activity.MainActivity;
import android.com.myqq.fragment.NetStateFragment;
import android.com.myqq.service.CountService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2017/5/3.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getExtras();
        final boolean netState =bundle.getBoolean("netState");
        final Activity myIntent = MainActivity.getMyIntent();
        final NetStateFragment netStateFragment = new NetStateFragment();
        if(netState == false)
        myIntent.runOnUiThread(new Runnable() {

            @Override
            public void run() {


                myIntent.findViewById(R.id.main_netState).setVisibility(View.VISIBLE);
            }
        });else {
            myIntent.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    myIntent.findViewById(R.id.main_netState).setVisibility(View.GONE);
                }
            });
        }

        Intent service = new Intent(context,CountService.class);
        context.startService(service);

    }





}