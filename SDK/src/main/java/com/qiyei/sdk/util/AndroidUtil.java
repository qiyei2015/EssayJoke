package com.qiyei.sdk.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Environment;

import com.qiyei.sdk.common.RuntimeEnv;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/9/3.
 * Version: 1.0
 * Description:
 */
public class AndroidUtil {

    /**
     * 判断是否是Debug状态
     * @param context
     * @return
     */
    public static boolean isDebug(Context context){
        boolean isDebug = context.getApplicationInfo()!=null&&
                (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        return isDebug;
    }

    /**
     * 获取App版本号
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context){
        String appVersion;
        try {
            appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion = "1.0.0";
        }
        return appVersion;
    }

    /**
     * 返回 data/data/packageName/files  不同系统上稍微有差异 ，例如8.0：/data/user/0/packageName/files
     * @return
     */
    public static String getInnerDataPath(){
        if (RuntimeEnv.appContext == null){
            throw new NullPointerException("RuntimeEnv.appContext is null");
        }
        return RuntimeEnv.appContext.getFilesDir().getAbsolutePath();
    }

    /**
     * 返回 Android/data/packageName/files 路径 ，完整路径 /storage/emulated/0/Android/data/packageName/files
     * @return
     */
    public static String getExternalDataPath(){
        if (RuntimeEnv.appContext == null){
            throw new NullPointerException("RuntimeEnv.appContext is null");
        }
        return RuntimeEnv.appContext.getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * 返回SD卡根路径 / ，完整路径：/storage/emulated/0
     * @return
     */
    public static String getSdCardDataPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

}
