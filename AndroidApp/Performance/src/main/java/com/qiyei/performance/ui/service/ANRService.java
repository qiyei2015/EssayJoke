package com.qiyei.performance.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.qiyei.framework.util.Utils;
import com.qiyei.sdk.log.LogManager;


public class ANRService extends Service {

    private static final String TAG = "ANRService";

    public ANRService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //Utils.writeFile();
//        LogManager.i(TAG,"onCreate start");
//        try {
//            Thread.sleep(21 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        LogManager.i(TAG,"onCreate end");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LogManager.i(TAG,"onStartCommand start");
        try {
            Thread.sleep(21 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogManager.i(TAG,"onStartCommand end");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
