package com.qiyei.sdk;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.common.ExceptionCrashHandler;
import com.qiyei.sdk.dc.DataManager;
import com.qiyei.sdk.fixbug.FixDexManager;
import com.qiyei.sdk.http.HttpManager;
import com.qiyei.sdk.http.okhttp.OkHttpEngine;
import com.qiyei.sdk.image.GlideImpl;
import com.qiyei.sdk.image.ImageManager;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.server.core.CoreService;
import com.qiyei.sdk.server.core.CoreWakeUpService;
import com.qiyei.sdk.server.core.RemoteCoreService;
import com.qiyei.sdk.util.ToastUtil;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/20.
 * Version: 1.0
 * Description: SDK管理器，不允许被继承，被修改
 */
public final class SDKManager {

    private static final String TAG = SDKManager.class.getSimpleName();

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

        //启动核心服务 2秒以后
        startCoreService(context);

        //初始化ToastUtil
        ToastUtil.init(RuntimeEnv.appContext);

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

    /**
     * 启动核心服务
     * @param context
     */
    private static void startCoreService(Context context){

        //获取系统的闹钟服务
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //2秒
        int sec = 3 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + sec;
        Intent receiver = new Intent(context,StartCoreServiceReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context,0,receiver,0);
        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);

    }

    /**
     * 启动核心服务的广播接收器
     */
    public static class StartCoreServiceReceiver extends BroadcastReceiver{

        public StartCoreServiceReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            LogManager.i(TAG,"StartCoreServiceReceiver receive intent:" + intent);

            //启动核心服务与远程服务
            Intent in = new Intent(context,CoreService.class);
            context.startService(in);
            in = new Intent(context,RemoteCoreService.class);
            context.startService(in);
            in = new Intent(context,CoreWakeUpService.class);
            context.startService(in);
        }
    }
}
