package com.qiyei.essayjoke;

import android.app.Application;

import com.qiyei.sdk.crash.ExceptionCrashHandler;
import com.qiyei.sdk.fixbug.FixDexManager;
import com.qiyei.sdk.http.HttpManager;
import com.qiyei.sdk.http.okhttp.OkHttpEngine;
import com.qiyei.sdk.util.ToastUtil;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/8.
 * Version: 1.0
 * Description:
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ToastUtil.init(this);
        ExceptionCrashHandler.getInstance().init(this);
        //初始化网络引擎
        HttpManager.init(new OkHttpEngine());

        //加载所有的修复包
        try {
            FixDexManager fixDexManager = new FixDexManager(this);
            fixDexManager.fixDex();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
