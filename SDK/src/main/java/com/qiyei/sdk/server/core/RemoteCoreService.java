package com.qiyei.sdk.server.core;


import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.server.base.BaseService;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/9/1.
 * Version: 1.0
 * Description: 核心服务的远程服务，用于双进程守护
 */
public class RemoteCoreService extends BaseService {

    /**
     * 调试标志
     */
    private static final String TAG = RemoteCoreService.class.getSimpleName();
    /**
     * 名字
     */
    public static final String name = TAG;

    /**
     * context
     */
    private Context mContext;

    /**
     * 服务连接回调
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogManager.i(TAG,"onServiceConnected RemoteCoreService");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //先启动核心服务
            startService(new Intent(mContext,CoreService.class));
            bindService(new Intent(mContext, CoreService.class),mServiceConnection, Context.BIND_IMPORTANT);
            LogManager.i(TAG,"onServiceDisconnected CoreService");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            LogManager.i(TAG,"startForeground");
            startForeground(1,new Notification());
        }
        mContext = this;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(mContext, CoreService.class),mServiceConnection, Context.BIND_IMPORTANT);
        LogManager.i(TAG,"bind CoreService");
        return START_STICKY;
    }

    @Override
    protected String getServiceName() {
        return name;
    }

    @Override
    protected void onServiceReady() {
        LogManager.i(TAG,"CoreService onServiceReady");
    }

    @Override
    protected void onClientDown(int id) {
        LogManager.i(TAG,"CoreService onClientDown id:" + id);
    }


}
