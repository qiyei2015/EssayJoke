package com.qiyei.sdk.log;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private static final String APP_INFO_NAME = "DeviceInfo.txt";
    /**
     * 崩溃的日志文件
     */
    private static final String CRASH_FILE = "crash_file";

    /**
     * 初始化块
     */
    static {
        //以当前进程名 为文件名
        sDefLogImpl = new LogImpl(RuntimeEnv.procName);
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
        //默认存储在 包名 + log 目录下
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

    /**
     * 保存获取的 软件信息，设备信息，异常信息到存储设备中
     * @param e
     * @return
     */
    public static String writeExceptionToFile(Throwable e) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();

        //添加时间,进程等信息 打印进程ID 线程ID
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = sf.format(new Date());
        String message = time + " "
                + ""+ android.os.Process.myPid() + "|" +""+ android.os.Process.myTid()
                + "[" + RuntimeEnv.getCurrentFileName() + "->" + RuntimeEnv.getCurrentMethodName()+"]"
                + " ";
        sb.append(message);

        //崩溃的详细信息
        sb.append(getExecptionInfo(e));

        Log.d(LogConstant.TAG,"writeExceptionToFile --> " + sb.toString());

        //保存文件 手机应用目录
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            //文件目录 包名/log/crash/
            File dir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()
                    + File.separator + RuntimeEnv.packageName + File.separator + LogConstant.SUFFIX + File.separator + "crash" + File.separator);
            //文件存在
            if (dir.exists()){
                FileUtil.deleteFile(dir);
            }
            //重新创建
            if (!dir.exists()){
                dir.mkdir();
            }
            //异常信息文件格式 时间 + .log
            fileName = dir.toString() + File.separator + getFormatTime("yyyy-MM-dd HH-mm-ss.SSS") + ".log";
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }finally {
                try {
                    if (fos != null){
                        fos.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return fileName;
    }

    /**
     * 获取CrashFile
     * @return
     */
    public static File getCrashFile(){
        String fileName = RuntimeEnv.appContext.getSharedPreferences(RuntimeEnv.appContext.getPackageName(), Context.MODE_PRIVATE)
                .getString(CRASH_FILE,"");
        return new File(fileName);
    }

    /**
     * 缓存crashFile文件
     * @param fileName
     */
    public static void cacheCrashFile(String fileName) {
        SharedPreferences sp = RuntimeEnv.appContext.getSharedPreferences(RuntimeEnv.appContext.getPackageName(),Context.MODE_PRIVATE);
        sp.edit().putString(CRASH_FILE,fileName).commit();

    }

    /**
     * 获取系统未捕捉的错误信息
     * @param throwable
     * @return
     */
    private static String getExecptionInfo(Throwable throwable){
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    /**
     * 获取格式化时间
     * @param formatStr
     * @return
     */
    private static String getFormatTime(String formatStr) {
        SimpleDateFormat df = new SimpleDateFormat(formatStr);
        return df.format(System.currentTimeMillis());
    }

}
