package com.qiyei.sdk.common;



import com.qiyei.sdk.log.LogManager;


/**
 * @author Created by qiyei2015 on 2017/5/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 崩溃时异常处理器
 */
public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {
    /**
     * 调试标志
     */
    private final static String TAG = ExceptionCrashHandler.class.getSimpleName();

    /**
     * 默认的线程ExceptionHandler
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private static class SingleHolder{
        private final static ExceptionCrashHandler sHandler = new ExceptionCrashHandler();
    }

    /**
     * 构造方法私有化
     */
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
     */
    public void init(){
        //设置全局的异常处理类为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);
        //获取当前线程默认的ExceptionHandler
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        LogManager.e(TAG,e.getMessage());

        //写入到本地文件 e 当前应用版本 手机信息

        // 1. 崩溃的详细信息
        // 2. 应用信息 包名 版本号
        // 3. 手机信息 系统版本 手机型号 内存
        // 4. 保存当乞丐了文件，等应用再次启动再上传(上传文件不在这里处理)

        String crashFile = LogManager.writeExceptionToFile(e);
        LogManager.e(TAG,"crashFile --> " + crashFile);
        // 缓存崩溃日志文件
        LogManager.cacheCrashFile(crashFile);
        uploadCrashLog();
        //让系统默认处理器处理
        mDefaultHandler.uncaughtException(t,e);
    }

    /**
     * 上传crash日志到服务器
     */
    private void uploadCrashLog(){


    }
}
