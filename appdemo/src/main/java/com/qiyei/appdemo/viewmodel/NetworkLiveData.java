package com.qiyei.appdemo.viewmodel;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.lang.ref.WeakReference;


/**
 * @author Created by qiyei2015 on 2018/2/25.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 监听网络是否连接的LiveData
 */
public class NetworkLiveData extends LiveData<Integer> {


    private static NetworkLiveData sNetworkLiveData;

    private WeakReference<Context> mContextWeakReference;

    private NetworkChangeReceiver mChangeReceiver;

    private NetworkLiveData(Context context){
        mContextWeakReference = new WeakReference<Context>(context);
    }

    /**
     * DCL 方式单例
     * @return
     */
    public static NetworkLiveData getInstance(Context context){
        if (sNetworkLiveData == null){
            synchronized (NetworkLiveData.class){
                if (sNetworkLiveData == null){
                    sNetworkLiveData = new NetworkLiveData(context);
                }
            }
        }
        return sNetworkLiveData;
    }

    /**
     * observer 从0 到1 调用
     */
    @Override
    protected void onActive() {
        super.onActive();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mChangeReceiver = new NetworkChangeReceiver();
        mContextWeakReference.get().registerReceiver(mChangeReceiver, filter);
    }

    /**
     * observer 从1 到0 调用
     */
    @Override
    protected void onInactive() {
        super.onInactive();
        mContextWeakReference.get().unregisterReceiver(mChangeReceiver);
    }

    /**
     * 网络状态变更的广播
     */
    private class NetworkChangeReceiver extends BroadcastReceiver {

        public final int wifi = 2, mobile = 1, none = 0;
        public int oldState = none;

        /**
         * 触发网络状态监听回调
         *
         * @param nowStatus 当前网络状态
         */
        private void setChange(int nowStatus) {
            oldState = nowStatus;
            sNetworkLiveData.setValue(oldState);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                Log.i("通知", "网络不可以用");
                setChange(none);
            } else if (mobNetInfo.isConnected()) {
                Log.i("通知", "仅移动网络可用");
                setChange(mobile);
            } else if (wifiNetInfo.isConnected()) {
                Log.i("通知", "Wifi网络可用");
                setChange(wifi);
            }
        }
    }


}
