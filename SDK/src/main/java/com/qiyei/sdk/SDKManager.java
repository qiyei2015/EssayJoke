package com.qiyei.sdk;

import android.app.Application;
import android.content.Context;

import com.qiyei.sdk.crash.ExceptionCrashHandler;
import com.qiyei.sdk.fixbug.FixDexManager;
import com.qiyei.sdk.http.HttpManager;
import com.qiyei.sdk.http.okhttp.OkHttpEngine;
import com.qiyei.sdk.image.GlideOperImpl;
import com.qiyei.sdk.image.ImageManager;
import com.qiyei.sdk.util.ToastUtil;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/20.
 * Version: 1.0
 * Description: SDK管理器，不允许被继承，被修改
 */
public final class SDKManager {

    /**
     * Application 类型的Context
     */
    public static Context sContext;

    /**
     *
     * @param context
     */
    public static void initSDK(Context context) throws Exception{
        if (context instanceof Application){
            sContext = context;
        }else {
            throw new Exception("please init SDK in your Application !");
        }

        ToastUtil.init(sContext);
        ExceptionCrashHandler.getInstance().init(sContext);

        ImageManager.getInstance().init(sContext,new GlideOperImpl());

        //初始化网络引擎
        HttpManager.init(new OkHttpEngine());

        //加载所有的修复包
        try {
            FixDexManager fixDexManager = new FixDexManager(sContext);
            fixDexManager.fixDex();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
