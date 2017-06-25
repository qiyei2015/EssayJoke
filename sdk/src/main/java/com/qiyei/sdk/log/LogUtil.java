package com.qiyei.sdk.log;


import android.util.Log;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/24.
 * Version: 1.0
 * Description:
 */
public class LogUtil {
    /**
     * Priority constant for the println method; use v.
     */
    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use e
     */
    public static final int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;

    /**
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int v(String tag, String msg) {
        return Log.v(tag,msg);
    }

    /**
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int d(String tag, String msg) {
        return Log.d(tag,msg);
    }

    /**
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int i(String tag, String msg) {
        return Log.i(tag,msg);
    }

    /**
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int w(String tag, String msg) {
        return Log.w(tag,msg);
    }

    /**
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int e(String tag, String msg) {
        return Log.e(tag,msg);
    }

}
