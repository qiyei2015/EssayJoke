package com.qiyei.sdk.log;

import com.qiyei.sdk.BuildConfig;
import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

/**
 * @author Created by qiyei2015 on 2020/1/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 腾讯Xlog 封装
 */
public class XLogImpl implements ILog{

    static {
        System.loadLibrary("c++_shared");
        System.loadLibrary("marsxlog");
        System.loadLibrary("native-lib");
    }

    public XLogImpl(String cachePath,String logPath) {
        //init xlog
        Xlog.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, "", 0, "");
        Xlog.setConsoleLogOpen(true);
        Log.setLogImp(new Xlog());
    }


    @Override
    public void print(int level, String tag, String msg) {

    }

    @Override
    public void setWriteFile(boolean isWrite) {

    }
}
