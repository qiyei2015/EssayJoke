package com.qiyei.appdemo.service;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.server.base.BaseService;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/30.
 * Version: 1.0
 * Description:
 */
public class TestService extends Service {

    private static final String TAG = TestService.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        LogManager.i(TAG,"onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogManager.i(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogManager.i(TAG,"onBind");
        return new Binder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogManager.i(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogManager.i(TAG,"onDestroy");
        super.onDestroy();
    }

//    @Override
//    protected void onServiceReady() {
//        LogManager.i(TAG,"TestService onServiceReady");
//    }
//
//    @Override
//    protected String getServiceName() {
//        return TAG;
//    }
//
//    @Override
//    protected void onClientDown(int id) {
//        LogManager.i(TAG,"TestService onClientDown id:" + id);
//    }



}
