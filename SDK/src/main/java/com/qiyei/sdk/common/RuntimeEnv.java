package com.qiyei.sdk.common;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.qiyei.sdk.log.LogManager;

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
     * 调试用的标志
     */
    private static final String TAG = RuntimeEnv.class.getSimpleName();

    /**
     * 运行时的Application 类型的Context
     */
    public static Context appContext = null;
    /**
     * 进程名 子进程将按照 主进程_子进程 显示
     */
    public static String procName = "";
    /**
     * 包名
     */
    public static String packageName = "";
    /**
     * 应用名称
     */
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
        Log.i(TAG,"packageName --> " + packageName);

        String[] names = packageName.split("\\.");
        if (names.length > 1){
            appName = names[names.length -1];
        }else {
            appName = "qiyei";
        }
        Log.i(TAG,"appName --> " + appName);
        //获取当前进程的pid
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : activityManager.getRunningAppProcesses()){
            //找到相同的pid就是当前进程了
            if (appProcessInfo.pid == pid){
                procName = appProcessInfo.processName;
            }
        }
        if (!TextUtils.isEmpty(procName)){
            // : 说明是子进程
            if (procName.contains(":")){
                //将 : 替换成 _
                procName = procName.replace(":","_");
            }
        }

        Log.i(TAG,"pid -- > " + pid + " procName --> " + procName);

        //初始化CrashHandler
        ExceptionCrashHandler.getInstance().init(RuntimeEnv.appContext);
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

    /***
     * 获取当前运行的类的方法 和行数
     * @return
     */
    public static String getCurrentMethodName() {
        StackTraceElement element = getCallLogManagerStackTrace();
        if (element != null){
            String methodName = element.getMethodName();
            int lineNumber = element.getLineNumber();
            return methodName + "() " + lineNumber;
        }
        return null;
    }

    /**
     * 获取当前运行的Class
     * @return
     */
    public static String getCurrentClassName() {
        StackTraceElement element = getCallLogManagerStackTrace();
        if (element != null){
            String clazz = element.getClassName();
            //去最后一个即 类的简名
            if (clazz.contains(".")){
                String strArray[] = clazz.split("\\.");
                clazz = strArray[strArray.length -1];
            }
            return clazz;
        }
        return null;
    }

    /**
     * 获取当前运行的Class
     * @return
     */
    public static String getCurrentFileName() {
        StackTraceElement element = getCallLogManagerStackTrace();
        if (element != null){
            String fileName = element.getFileName();
            return fileName;
        }
        return null;
    }

    /**
     * 获取调用LogManager的调用栈
     * @return
     */
    private static StackTraceElement getCallLogManagerStackTrace(){
        int level = 0;
        //LogManager的全限定名称
        String clazzName = LogManager.class.getCanonicalName();
        //方法数组
        String array[] = new String[]{"v","d","i","w","e"};

        StackTraceElement[] stacks = new Throwable().getStackTrace();
        //依次寻找，找到LogManager的上一级
        for (level = 0 ;level < stacks.length;level++){
            String method = stacks[level].getMethodName();

            if (clazzName.equals(stacks[level].getClassName()) && (method.equals(array[0])
                    || method.equals(array[1]) || method.equals(array[2])
                    || method.equals(array[3]) || method.equals(array[4]))){
                break;
            }
        }

        //返回上一级的调用栈
        if (stacks.length > (level + 1)){
            return stacks[level +1];
        }
        return null;
    }
}
