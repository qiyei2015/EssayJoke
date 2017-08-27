package com.qiyei.sdk;

import android.app.Application;
import android.content.Context;

import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.common.ExceptionCrashHandler;
import com.qiyei.sdk.dc.DataManager;
import com.qiyei.sdk.fixbug.FixDexManager;
import com.qiyei.sdk.http.HttpManager;
import com.qiyei.sdk.http.okhttp.OkHttpEngine;
import com.qiyei.sdk.image.GlideImpl;
import com.qiyei.sdk.image.ImageManager;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.ToastUtil;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/20.
 * Version: 1.0
 * Description: SDK管理器，不允许被继承，被修改
 */
public final class SDKManager {

    /**
     *
     * @param context
     */
    public static void initSDK(Context context) throws Exception{
        if (context instanceof Application){
            //初始化运行时环境
            RuntimeEnv.init(context);
        }else {
            throw new Exception("please init SDK in your Application !");
        }

        //开始日志初始化,写设备信息，应用信息
        LogManager.writeAppInfo();

        //初始化ToastUtil
        ToastUtil.init(RuntimeEnv.appContext);

        //初始化CrashHandler
        ExceptionCrashHandler.getInstance().init(RuntimeEnv.appContext);

        //初始化DataCenter
        DataManager.getInstance();

        //初始化图片加载框架
        ImageManager.getInstance().init(RuntimeEnv.appContext,new GlideImpl());

        //初始化网络引擎
        HttpManager.init(new OkHttpEngine());

        //加载所有的修复包
        try {
            FixDexManager fixDexManager = new FixDexManager(RuntimeEnv.appContext);
            fixDexManager.fixDex();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
