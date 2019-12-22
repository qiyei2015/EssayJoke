package com.qiyei.framework.util;

import android.content.Context;

/**
 * @author Created by qiyei2015 on 2018/9/14.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class SingleInstance {

    private volatile static SingleInstance sInstance;

    private Context mContext;

    private SingleInstance(Context context){
        mContext = context;
    }

    public static SingleInstance getInstance(Context context){
        if (sInstance == null){
            synchronized (SingleInstance.class){
                if (sInstance == null){
                    sInstance = new SingleInstance(context);
                }
            }
        }
        return sInstance;
    }
}
