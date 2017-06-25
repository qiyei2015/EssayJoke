package com.qiyei.sdk.crash;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import com.qiyei.sdk.log.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/7.
 * Version: 1.0
 * Description:
 */
public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    private final static String TAG = ExceptionCrashHandler.class.getSimpleName();
    private Context mContext;

    private static final String CRASH_FILE = "crash_file";

    /**
     * 默认的线程ExceptionHandler
     */
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    private static class SingleHolder{
        private final static ExceptionCrashHandler sHandler = new ExceptionCrashHandler();
    }

    private ExceptionCrashHandler(){

    }

    /**
     * 内部类的方式提供单例
     * @return
     */
    public static ExceptionCrashHandler getInstance(){
        return SingleHolder.sHandler;
    }

    /**
     * 初始化，一般在Application中调用
     * @param context
     */
    public void init(Context context){
        mContext = context;
        //设置全局的异常处理类为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);
        //获取当前线程默认的ExceptionHandler
        mDefaultUncaughtExceptionHandler = Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LogUtil.e(TAG,e.getMessage());

        //写入到本地文件 e 当前应用版本 手机信息

        // 1. 崩溃的详细信息
        // 2. 应用信息 包名 版本号
        // 3. 手机信息 系统版本 手机型号 内存
        // 4. 保存当乞丐了文件，等应用再次启动再上传(上传文件不在这里处理)

        String crashFile = saveExceptionToSD(e);
        LogUtil.e(TAG,"crashFile --> " + crashFile);
        // 缓存崩溃日志文件
        cacheCrashFile(crashFile);

        //让系统默认处理器处理
        mDefaultUncaughtExceptionHandler.uncaughtException(t,e);
    }

    /**
     * 保存获取的 软件信息，设备信息，异常信息到存储设备中
     * @param e
     * @return
     */
    private String saveExceptionToSD(Throwable e) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();

        //手机信息 + 应用信息
        for (Map.Entry<String,String> entry : obtainSimpleInfo(mContext).entrySet()){
            sb.append(entry.getKey() + " = ").append(entry.getValue()).append("\n");
        }
        //崩溃的详细信息
        sb.append(obtainExecptionInfo(e));

        //保存文件 手机应用目录
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            //文件目录 包名/data/crash/
            File dir = new File(mContext.getExternalFilesDir(null) + File.separator + "crash" + File.separator);
            //文件存在
            if (dir.exists()){
                deleteFile(dir);
            }
            //重新创建
            if (!dir.exists()){
                dir.mkdir();
            }

            fileName = dir.toString() + File.separator + getAssignTime("yyyy_MM_dd_HH_mm") + ".txt";
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
    public File getCrashFile(){
        String fileName = mContext.getSharedPreferences(mContext.getPackageName(),Context.MODE_PRIVATE)
                .getString(CRASH_FILE,"");
        return new File(fileName);
    }

    /**
     * 缓存crashFile文件
     * @param fileName
     */
    private void cacheCrashFile(String fileName) {
        SharedPreferences sp = mContext.getSharedPreferences(mContext.getPackageName(),Context.MODE_PRIVATE);
        sp.edit().putString(CRASH_FILE,fileName).commit();

    }

    /**
     * 获取一些简单的信息,软件版本，手机版本，型号等信息存放在HashMap中
     * @param context
     * @return
     */
    private Map<String,String> obtainSimpleInfo(Context context){
        Map<String,String> map = new HashMap<>();
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(),PackageManager.GET_ACTIVITIES);
            if (packageInfo != null){
                map.put("versionName",packageInfo.versionName);
                map.put("versionCode", "" + packageInfo.versionCode);
                map.put("MODEL", "" + Build.MODEL);
                map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
                map.put("PRODUCT", "" + Build.PRODUCT);
                map.put("MOBLE_INFO", getDeviceInfo());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取设备信息
     * @return
     */
    private String getDeviceInfo(){
        StringBuffer sb = new StringBuffer();
        Field[] fields = Build.class.getDeclaredFields();
        try {
            for (Field field : fields){
                field.setAccessible(true);
                sb.append(field.getName());
                sb.append(" = ");
                sb.append(field.get(null).toString());
                sb.append("\n");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取系统未捕捉的错误信息
     * @param throwable
     * @return
     */
    private String obtainExecptionInfo(Throwable throwable){
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    /**
     * 递归删除文件或目录
     * @param fileName
     * @return
     */
    private boolean deleteFile(File fileName) {
        boolean success = false;
        if (fileName.isDirectory()){
            File[] files = fileName.listFiles();
            for (File file : files){
                if (!deleteFile(file)){
                    success = false;
                    break;
                }
            }
        }else {
            success = fileName.delete();
        }
        return success;
    }

    /**
     * 获取时间
     * @param formatStr
     * @return
     */
    private String getAssignTime(String formatStr) {
        SimpleDateFormat df = new SimpleDateFormat(formatStr);
        return df.format(System.currentTimeMillis());
    }
}
