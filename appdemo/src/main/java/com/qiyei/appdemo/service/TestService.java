package com.qiyei.appdemo.service;


import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.server.base.BaseService;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/30.
 * Version: 1.0
 * Description:
 */
public class TestService extends BaseService {

    private static final String TAG = TestService.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    protected void onServiceReady() {
        LogManager.i(TAG,"TestService onServiceReady");
    }

    @Override
    protected String getServiceName() {
        return TAG;
    }

    @Override
    protected void onClientDown(int id) {
        LogManager.i(TAG,"TestService onClientDown id:" + id);
    }

}
