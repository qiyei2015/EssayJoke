package com.qiyei.sdk.log;

import android.os.Environment;
import android.util.Log;

import com.qiyei.sdk.common.RuntimeEnv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/29.
 * Version: 1.0
 * Description:
 */
public class LogManager {

    /**
     * 保存日志实现的Map，可用于多个文件，多个进程使用
     */
    private static Map<String,LogImpl> sLogMap = new HashMap<>();
    /**
     * 默认的日志实现对象
     */
    private static LogImpl sDefLogImpl = null;
    /**
     * 默认的应用信息
     */
    private static final String APP_INFO_NAME = "appInfo.txt";

    static {
        //以当前包名为文件名
        sDefLogImpl = new LogImpl(RuntimeEnv.packageName);
        sLogMap.put(LogConstant.DEF_NAME,sDefLogImpl);
    }

    /**
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int v(String tag, String msg) {
        return printLog(LogConstant.VERBOSE,tag,msg);
    }

    /**
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int d(String tag, String msg) {
        return printLog(LogConstant.DEBUG,tag,msg);
    }

    /**
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int i(String tag, String msg) {
        return printLog(LogConstant.INFO,tag,msg);
    }

    /**
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int w(String tag, String msg) {
        return printLog(LogConstant.WARN,tag,msg);
    }

    /**
     *
     * @param tag
     * @param msg
     * @return
     */
    public static int e(String tag, String msg) {
        return printLog(LogConstant.ERROR,tag,msg);
    }

    /**
     * 日志打印函数
     * @param level
     * @param tag
     * @param msg
     * @return
     */
    private static int printLog(int level,String tag, String msg){
        return sDefLogImpl.print(level,tag,msg);
    }

    /**
     * 日志打印函数
     * @param file
     * @param level
     * @param tag
     * @param msg
     * @return
     */
    private static int printLog(String file,int level,String tag, String msg){
        return 0;
    }

    /**
     * 写默认的应用信息
     */
    public static void writeAppInfo(){
        File dir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()
                + File.separator + RuntimeEnv.packageName + File.separator + LogConstant.SUFFIX);
        if (!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(dir,APP_INFO_NAME);
        //如果文件存在，表明已经存储过了，不必再写
        if (file.exists()){
            return;
        }
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter(file,true),true);
            StringBuilder sb = new StringBuilder();
            //设备信息 + 应用信息
            for (Map.Entry<String,String> entry : RuntimeEnv.getAppInfo().entrySet()){
                sb.append(entry.getKey() + " = ").append(entry.getValue()).append("\n");
            }
            Log.i(LogConstant.TAG,"AppInfo --> " + sb.toString());
            printWriter.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            printWriter.close();
        }
    }

}
