package com.qiyei.sdk.server.core;

import android.content.Context;
import android.os.IBinder;


import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.log.LogManager;


/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/9/7.
 * Version: 1.0
 * Description: 核心服务Binder的管理
 */
public class CoreBinderManager {
    /**
     * 调试标志
     */
    private static final String TAG = CoreBinderConstant.TAG;
    /**
     * Context
     */
    private Context mContext;
    /**
     * Binder连接池
     */
    private CoreBinderPool mBinderPool;

    /**
     * 内部类
     */
    private static class SingleHolder{
        static final CoreBinderManager sInstance = new CoreBinderManager(RuntimeEnv.appContext);
    }

    /**
     * 构造方法私有化
     */
    private CoreBinderManager(Context context){
        mContext = context.getApplicationContext();
        mBinderPool = new CoreBinderPool(context);
    }

    /**
     * 内部类方式单例
     * @return
     */
    public static CoreBinderManager getInstance(){
        return SingleHolder.sInstance;
    }

    /**
     * 根据名称查询服务
     * @param name
     * @return
     */
    public IBinder queryBinder(String name){
        IBinder binder = mBinderPool.queryBinder(name);
        LogManager.i(TAG,"queryBinder name : " + name + ",binder:" + binder);
        return binder;
    }

}
