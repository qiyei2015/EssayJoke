package com.qiyei.sdk.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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

    public static String appName = "";

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

        if (TextUtils.isEmpty(packageName)){
            packageName = "com.qiyei";
        }
        String[] names = packageName.split("\\.");
        if (names.length > 1){
            appName = names[names.length -1];
        }else {
            appName = "qiyei";
        }
    }


    /**
     * 获取一些简单的信息,软件版本，手机版本，型号等信息存放在HashMap中
     * @return
     */
    public static Map<String,String> getAppInfo(){
        Map<String,String> map = new HashMap<>();
        PackageManager packageManager = appContext.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(appContext.getPackageName(),PackageManager.GET_ACTIVITIES);
            if (packageInfo != null){
                map.put("versionName",packageInfo.versionName);
                map.put("versionCode", "" + packageInfo.versionCode);
                map.put("MODEL", "" + Build.MODEL);
                map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
                map.put("PRODUCT", "" + Build.PRODUCT);
                map.put("MOBLE_INFO", getDeviceInfo());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取设备信息
     * @return
     */
    private static String getDeviceInfo(){
        StringBuffer sb = new StringBuffer();
        Field[] fields = Build.class.getDeclaredFields();
        try {
            for (Field field : fields){
                field.setAccessible(true);
                sb.append(field.getName());
                sb.append(" = ");
                sb.append(field.get(null).toString());
                sb.append("\n");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
