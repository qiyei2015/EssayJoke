package com.qiyei.essayjoke;

import android.app.Application;
import android.widget.Toast;

import com.qiyei.baselibrary.ExceptionCrashHandler;
import com.qiyei.baselibrary.fixbug.FixDexManager;
import com.qiyei.baselibrary.util.ToastUtil;

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

        //加载所有的修复包
        try {
            FixDexManager fixDexManager = new FixDexManager(this);
            fixDexManager.fixDex();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
