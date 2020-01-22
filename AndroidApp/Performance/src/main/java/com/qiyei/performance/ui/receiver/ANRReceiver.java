package com.qiyei.performance.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.qiyei.sdk.log.LogManager;

public class ANRReceiver extends BroadcastReceiver {

    private static final String TAG = "ANRReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        LogManager.i(TAG,"onReceive start");

        final PendingResult result = goAsync();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                result.finish();
//            }
//        }).start();
        try {
            Thread.sleep(6 *1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogManager.i(TAG,"onReceive end");
    }

    public int getCount(ViewGroup group){
        int childCount = group.getChildCount();
        //如果包含ViewGroup sum =1
        int sum = 0;

        for (int i = 0 ;i < childCount ;i++){
            View child = group.getChildAt(i);
            if (child instanceof ViewGroup){
                sum += getCount((ViewGroup) child);
            } else {
                sum++;
            }
        }
        return sum;
    }

}
