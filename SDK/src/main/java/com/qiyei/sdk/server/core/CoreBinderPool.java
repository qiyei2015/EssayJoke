package com.qiyei.sdk.server.core;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;


import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.server.base.IBinderPool;
import com.qiyei.sdk.server.binder.ComputeImpl;

import java.util.concurrent.CountDownLatch;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/9/4.
 * Version: 1.0
 * Description:
 */
public class CoreBinderPool implements IBinderPool{

    private static final String TAG = CoreBinderConstant.TAG;

    private Context mContext;

    private ICoreBinderPool mBinderPool;

    /**
     * 所要连接的Service
     */
    private Class<?> mService;
    /**
     * 用于同步的
     */
    private CountDownLatch mDownlatch;

    /**
     * 绑定服务的连接
     */
    private ServiceConnection mConnecttion = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = ICoreBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mDeathRecipient,0);
                //计数器减1
                mDownlatch.countDown();
                LogManager.i(CoreBinderConstant.TAG,"CoreBinderPool onServiceConnected name: " + name + ", service :" + service);
            } catch (RemoteException e) {
                e.printStackTrace();
                LogManager.w(TAG,LogManager.getExecptionInfo(e));
            }

            //添加服务 连接成功了才能添加
            addBinder(CoreBinderConstant.compute,new ComputeImpl());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * 构造方法
     * @param context
     */
    public CoreBinderPool(Context context){
        mContext = context;
        attachService(CoreService.class);
    }

    /**
     * Binder的生命周期监听
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            //重连
            LogManager.w(TAG,"binder die");
            //解除注册
            mBinderPool.asBinder().unlinkToDeath(mDeathRecipient,0);
            mBinderPool = null;
            //重新绑定服务
            connectCoreService();
        }
    };


    /**
     * 链接Service
     * @param service
     */
    protected void attachService(Class<?> service){
        mService = service;
        connectCoreService();
    }

    /**
     * 连接核心服务
     */
    private synchronized void connectCoreService() {
        //1个计数 表示同时只能运行一个
        mDownlatch = new CountDownLatch(1);

        if (mService == null){
            LogManager.w(TAG,"bind service failed ! service is null");
            return;
        }

        Intent intent = new Intent(mContext,mService);

        //绑定CoreService
        if (mContext.bindService(intent,mConnecttion,Context.BIND_AUTO_CREATE)){
            LogManager.i(TAG,"bind service success ! service:" + mService);
            //阻塞到这
//            try {
//                mDownlatch.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }else {
            LogManager.w(TAG,"bind service failed ! service:" + mService);
        }

    }

    /**
     * 根据名称查询服务
     * @param name
     * @return
     */
    @Override
    public IBinder queryBinder(String name){
        IBinder binder = null;
        try {
            if (mBinderPool != null){
                binder = mBinderPool.queryBinder(name);
            }
            LogManager.i(TAG,"queryBinder,mBinderPool:" + mBinderPool);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        LogManager.i(TAG,"queryBinder,binder:" + binder);
        return binder;
    }

    /**
     * 添加Binder
     * @param name
     * @param binder
     * @return
     */
    @Override
    public void addBinder(String name, IBinder binder){
        try {
            if (mBinderPool != null){
                mBinderPool.addBinder(name,binder);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除Binder
     * @param name
     */
    @Override
    public void removeBinder(String name){
        try {
            if (mBinderPool != null){
                mBinderPool.removeBinder(name);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
