package com.qiyei.sdk.common;

import android.content.Context;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/24.
 * Version: 1.0
 * Description: 运行时环境参数
 */
public final class RuntimeEnv {

    /**
     * 运行时的Application 类型的Context
     */
    public static Context appContext = null;


    public static String procName = "";

    public static String packageName = "";

    /**
     * 初始化
     * @param context
     */
    public static void init(Context context){
        if (context == null){
            throw new NullPointerException("RuntimeEnv context is null");
        }

        /**
         * 防止多次初始化
         */
        if (appContext != null){
            return;
        }
        //初始化appContext
        appContext = context.getApplicationContext();
        //初始化包名
        packageName = context.getPackageName();

    }

}
