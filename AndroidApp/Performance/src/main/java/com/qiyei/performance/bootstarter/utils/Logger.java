package com.qiyei.performance.bootstarter.utils;


import android.util.Log;

/**
 * @author Created by qiyei2015 on 2019/11/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class Logger {

    private static final String TAG = "BOOT_STARTER";
    private static boolean sDebug = true;

    public static void d(String tag,String msg) {
        if (!sDebug) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[")
                .append(tag)
                .append("] ")
                .append(msg);
        Log.d(TAG,builder.toString());
    }

    public static boolean isDebug() {
        return sDebug;
    }

    public static void setDebug(boolean debug) {
        sDebug = debug;
    }

}
