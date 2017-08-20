package com.qiyei.essayjoke;

import android.app.Application;

import com.qiyei.framework.skin.SkinManager;
import com.qiyei.sdk.SDKManager;
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
        try {
            SDKManager.initSDK(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //初始化皮肤管理器
        SkinManager.getInstance().init(this);
    }

}
