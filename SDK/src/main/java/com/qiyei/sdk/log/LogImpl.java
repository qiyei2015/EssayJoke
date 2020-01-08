package com.qiyei.sdk.log;

import android.util.Log;

import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.util.AndroidUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Created by qiyei2015 on 2017/8/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 日志打印的具体实现
 */
public class LogImpl implements ILog{

    /**
     * 级别,高于次级别的日志都会被打印
     */
    private int mLevel = LogConstant.DEBUG;

    /**
     * 打印流到文件
     */
    private PrintWriter mPrintWriter;

    /**
     * 日志开关是否打开
     */
    private boolean isOpen = true;
    /**
     * 是否写入文件
     */
    private boolean isWriteFile;



    /**
     * 构造方法
     * @param path
     */
    public LogImpl(String path){
        try {
            File file = new File(path);
            mPrintWriter = new PrintWriter(new FileWriter(file,true),true);
        } catch (IOException e) {
            Log.i(LogConstant.TAG,"PrintWriter IOException ");
            e.printStackTrace();
        }
    }

    /**
     * 打印日志
     * @param level
     * @param tag
     * @param msg
     * @return
     */
    @Override
    public void print(int level,String tag, String msg){
        //如果是非调试状态，直接不打印，返回
        if (!AndroidUtil.isDebug(RuntimeEnv.appContext) && !isOpen){
            return ;
        }

//        Log.v(LogConstant.TAG,"log print --> level:" + level);
        //大于等于该级别才会打印
        if (level >= mLevel){
            if (isWriteFile){
                //打印到文件 只有设置了为true才会打印到文件
                printToFile(level,tag,msg);
            }
            //打印到控制台
            printToConsole(level,tag, msg);
        }
    }

    @Override
    public void setWriteFile(boolean writeFile) {
        isWriteFile = writeFile;
    }

    /**
     * 打印到日志文件中
     * @param level
     * @param tag
     * @param msg
     */
    private void printToFile(int level, String tag, String msg){
        printMessage(LogHelper.formatLog(level,tag,msg));
    }

    /**
     * 控制台中打印
     * @param level
     * @param tag
     * @param msg
     */
    private int printToConsole(int level, String tag, String msg){

        //直接根据级别调用android原生的打印
        switch (level){
            case LogConstant.VERBOSE:
                Log.v(tag,msg);
                break;
            case LogConstant.DEBUG:
                Log.d(tag,msg);
                break;
            case LogConstant.INFO:
                Log.i(tag,msg);
                break;
            case LogConstant.WARN:
                Log.w(tag,msg);
                break;
            case LogConstant.ERROR:
                Log.e(tag,msg);
                break;
            case LogConstant.ASSERT:
                break;
            default:
                break;
        }
        return 0;
    }

    /**
     * 打印消息 ,需要考虑到同步
     * @param msg
     */
    private void printMessage(String msg){
        synchronized (mPrintWriter){
            mPrintWriter.println(msg);
        }
    }

}
