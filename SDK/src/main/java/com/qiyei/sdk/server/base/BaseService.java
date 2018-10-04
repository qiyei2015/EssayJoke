package com.qiyei.sdk.server.base;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.server.base.IMessageListener;
import com.qiyei.sdk.server.base.IMessageManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/30.
 * Version: 1.0
 * Description: 所有服务的基类
 */
public abstract class BaseService extends Service{

    private static final String TAG = BaseService.class.getSimpleName();

    /**
     * Service的Binder
     */
    private Binder mBinder;

    /**
     * 绑定自己的链接
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            LogManager.i(TAG,"onServiceConnected ComponentName:" + name + ", Binder:" + service);
            mBinder = (Binder) service;
            //添加服务到服务管理Map中
            ServerManager.getInstance().addService(getServiceName(),name,mBinder);
            onServiceReady();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogManager.i(TAG,"onServiceDisconnected ComponentName:" + name);
            ServerManager.getInstance().removeService(getServiceName());
        }
    };

    /**
     * 监听Client端的消息回调的监听器
     */
    private RemoteCallbackList<IMessageListener> mMessageListenerCallbackList = new RemoteCallbackList<IMessageListener>(){

        //当Binder断开时取消注册
        @Override
        public void onCallbackDied(IMessageListener callback, Object cookie) {
            super.onCallbackDied(callback, cookie);
            int id = 0;
            if (cookie != null && cookie instanceof Integer){
                id = (Integer) cookie;
            }
            //取消注册
            unregister(callback);
            //客户端挂掉的通知
            onClientDown(id);
            LogManager.w(TAG,"onCallbackDied clientId -->" + id);
        }
    };

    /**
     * 管理MessageListener的Binder
     */
    private Binder mMessageManagerBinder = new IMessageManager.Stub(){

        /**
         * 用来计数
         */
        private AtomicInteger mAtomicInteger = new AtomicInteger(1);

        @Override
        public void registerListener(IMessageListener listener) throws RemoteException {
            mMessageListenerCallbackList.register(listener,mAtomicInteger.getAndIncrement());
            LogManager.i(TAG,"registerListener listener:" + listener );
        }

        @Override
        public void unregisterListener(IMessageListener listener) throws RemoteException {
            mMessageListenerCallbackList.unregister(listener);
            LogManager.i(TAG,"unregisterListener listener:" + listener );
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        //启动就绑定自己
        bindToMyself();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null){
            LogManager.i(TAG,"onStartCommand intent is null");
            return 0;
        }

        if (mBinder != null){
            //添加服务
            ServerManager.getInstance().addService(getServiceName(),new ComponentName(this,this.getClass()),mBinder);
            LogManager.i(TAG,"addService serviceName:" + getServiceName());
        }
        LogManager.i(TAG,"onStartCommand serviceName:" + getServiceName());

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMessageListenerCallbackList.kill();
        LogManager.i(TAG," onDestroy:" + getServiceName());
    }

    /**
     * 通知客户端 server调用
     * @param message
     */
    protected void notifyClient(BMessage message){
        int size = mMessageListenerCallbackList.beginBroadcast();
        LogManager.i(TAG,"notifyClient,BMessage:" + message);
        for (int i = 0;i <size;i++){
            IMessageListener listener = mMessageListenerCallbackList.getBroadcastItem(i);
            try {
                listener.onProcessMessage(message);
                LogManager.i(TAG,"notifyClient,listener:"+ listener.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mMessageListenerCallbackList.finishBroadcast();
    }

    /**
     * 获取MessageManager的Binder，一般为客户端调用
     * @return
     */
    public Binder getMessageManagerBinder(){
        return mMessageManagerBinder;
    }

    /**
     * 返回Service名称
     * @return
     */
    protected abstract String getServiceName();

    /**
     * Service已经就绪，一般用于通知其他模块
     */
    protected abstract void onServiceReady();

    /**
     * 客户端挂掉
     * @param id
     */
    protected abstract void onClientDown(int id);

    /**
     * 绑定自己
     */
    private void bindToMyself(){
        //自己的intent
        Intent intent = new Intent(this,this.getClass());
        LogManager.i(TAG,"bindToMyself,intent:" + intent);
        if (bindService(intent,mConnection, Context.BIND_AUTO_CREATE)){
            LogManager.i(TAG,"bindToMyself success ");
        }else {
            LogManager.i(TAG,"bindToMyself fail ");
        }
    }

}
