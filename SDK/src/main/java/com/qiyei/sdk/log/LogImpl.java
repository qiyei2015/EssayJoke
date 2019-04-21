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
public class LogImpl {

    /**
     * 文件名称
     */
    private String mFileName;
    /**
     * 文件后缀名
     */
    private static final String SUFFIX_NAME = ".log";
    /**
     * 文件路径
     */
    private String mPath;
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
     * 日志打印的标签
     */
    private static final String V = "V";
    private static final String D = "D";
    private static final String I = "I";
    private static final String W = "W";
    private static final String E = "E";
    private static final String A = "A";

    /**
     * 构造方法
     * @param fileName
     */
    public LogImpl(String fileName){
        mFileName = fileName;
        //先创建文件夹 默认存储在 包名 + log 目录下
        File dir = new File(AndroidUtil.getExternalDataPath() + File.separator + LogConstant.SUFFIX);
        if (!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(dir,mFileName + SUFFIX_NAME);

        mPath = file.getAbsolutePath();

        try {
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
    public int print(int level,String tag, String msg){

        //如果是非调试状态，直接不打印，返回
        if (!AndroidUtil.isDebug(RuntimeEnv.appContext) && !isOpen){
            return 0;
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
        return 0;
    }

    /**
     * 打印到日志文件中
     * @param level
     * @param tag
     * @param msg
     */
    private void printToFile(int level, String tag, String msg){
        // 时间格式 2017-8-26 23.22.23.445
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = sf.format(new Date());
        String preTAG = V;

        switch (level) {
            case LogConstant.VERBOSE:
                preTAG = V;
                break;
            case LogConstant.DEBUG:
                preTAG = D;
                break;
            case LogConstant.INFO:
                preTAG = I;
                break;
            case LogConstant.WARN:
                preTAG = W;
                break;
            case LogConstant.ERROR:
                preTAG = E;
                break;
            case LogConstant.ASSERT:
                preTAG = A;
                break;
            default:
                break;
        }
        //打印进程ID 线程ID 当前类 当前方法
        String message = time + " " + preTAG + " "
                + ""+ android.os.Process.myPid() + "|" + android.os.Process.myTid()
                + "[" + RuntimeEnv.getCurrentFileName() + "->" + RuntimeEnv.getCurrentMethodName()+"]"
                + "[" + tag +"]" + msg;
        printMessage(message);
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

    /**
     * @return {@link #isOpen}
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * @param open the {@link #isOpen} to set
     */
    public void setOpen(boolean open) {
        isOpen = open;
    }

    /**
     * @return {@link #isWriteFile}
     */
    public boolean isWriteFile() {
        return isWriteFile;
    }

    /**
     * @param writeFile the {@link #isWriteFile} to set
     */
    public void setWriteFile(boolean writeFile) {
        isWriteFile = writeFile;
    }

    /**
     * @return {@link #mLevel}
     */
    public int getLevel() {
        return mLevel;
    }

    /**
     * @param level the {@link #mLevel} to set
     */
    public void setLevel(int level) {
        mLevel = level;
    }

    /**
     * @return {@link #mPath}
     */
    public String getPath() {
        return mPath;
    }

}
