package com.qiyei.sdk.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

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
}
