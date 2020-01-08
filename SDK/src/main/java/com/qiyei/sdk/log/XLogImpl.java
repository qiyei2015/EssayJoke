package com.qiyei.sdk.log;


import com.qiyei.sdk.common.RuntimeEnv;
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
        //System.loadLibrary("native-lib");
    }

    public XLogImpl(String cachePath,String logPath) {
        //init xlog
        Xlog.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, "", 0, "");
        Xlog.setConsoleLogOpen(true);
        //10M
        Xlog.setMaxFileSize(10*1024*1024);
        //Log.setLogImp(new Xlog());
    }


    @Override
    public void print(int level, String tag, String msg) {
        switch (level){
            case LogConstant.VERBOSE:
                Log.v("",LogHelper.formatXLog(level,tag,msg));
                break;
            case LogConstant.DEBUG:
                Log.d("",LogHelper.formatXLog(level,tag,msg));
                break;
            case LogConstant.INFO:
                //Log.i(tag,LogHelper.formatXLog(level,tag,msg));
                Xlog.logWrite2(level - 1,tag, RuntimeEnv.getCurrentFileName(),RuntimeEnv.getCurrentMethodName2()
                        ,RuntimeEnv.getCurrentLineNumber(),android.os.Process.myPid(),android.os.Process.myTid()
                        ,android.os.Process.myTid(),msg);
                break;
            case LogConstant.WARN:
                Log.w("",LogHelper.formatXLog(level,tag,msg));
                break;
            case LogConstant.ERROR:
                Log.e("",LogHelper.formatXLog(level,tag,msg));
                break;
            case LogConstant.ASSERT:
                break;
            default:
                break;
        }
    }

    @Override
    public void setWriteFile(boolean isWrite) {

    }
}
