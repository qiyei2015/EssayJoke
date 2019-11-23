package com.qiyei.performance.bootstarter.utils;

import android.app.ActivityManager;
import android.content.Context;


/**
 * @author Created by qiyei2015 on 2019/11/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ProcessUtils {

    public static boolean isMainProcss(Context context){
        int pid = android.os.Process.myPid();
        String packageName = context.getPackageName();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : mActivityManager.getRunningAppProcesses()){
            if (appProcessInfo.pid == pid){
                if (packageName.equals(appProcessInfo.processName)){
                    return true;
                }
            }
        }
        return false;
    }
}
