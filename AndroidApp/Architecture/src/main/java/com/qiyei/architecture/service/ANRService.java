package com.qiyei.architecture.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.qiyei.architecture.util.Utils;

public class ANRService extends Service {

    public ANRService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Utils.writeFile();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}
