package com.qiyei.mall;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qiyei.framework.FrameworkApplication;
import com.qiyei.sdk.launchstarter.LauncherManager;
import com.qiyei.sdk.log.LogManager;

import org.jetbrains.annotations.Nullable;

import cn.jpush.android.api.JPushInterface;

/**
 * @author Created by qiyei2015 on 2019/4/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MallApplication extends FrameworkApplication {

    @Override
    protected void attachBaseContext(@Nullable Context base) {
        super.attachBaseContext(base);
        LauncherManager.getDefault().start();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
        LogManager.d("TAG","MallApplication onCreate");
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

}
