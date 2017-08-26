package com.qiyei.essayjoke.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.qiyei.essayjoke.ProcessConnection;
import com.qiyei.sdk.util.ToastUtil;

public class RemoteService extends Service {
    private static final String TAG = "Service";
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub(){
            @Override
            public void connect() throws RemoteException {
                ToastUtil.showLongToast("RemoteService 已经建立连接 ！");
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(RemoteService.this, LocalService.class),mServiceConnection, Context.BIND_IMPORTANT);
        LogManager.d(TAG,"LocalService 已经启动");
        return START_STICKY;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ToastUtil.showLongToast("与LocalService 已经建立连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(RemoteService.this, LocalService.class));
            bindService(new Intent(RemoteService.this, LocalService.class),mServiceConnection,Context.BIND_IMPORTANT);
        }
    };

}
