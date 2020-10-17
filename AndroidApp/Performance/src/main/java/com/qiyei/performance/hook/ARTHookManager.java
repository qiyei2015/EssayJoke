package com.qiyei.performance.hook;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.util.Log;
import android.widget.ImageView;

import com.qiyei.sdk.log.LogManager;

import de.robv.android.xposed.DexposedBridge;
import de.robv.android.xposed.XC_MethodHook;


/**
 * @author Created by qiyei2015 on 2019/11/20.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ARTHookManager {

    private static final String TAG = "ARTHookManager";

    private static final int IPC_TIME_OUT = 5;

    static class ImageViewMethodHook extends XC_MethodHook {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            //得到ImageView对象
            ImageView imageView = (ImageView) param.thisObject;
            //得到Bitmap对象
            Bitmap bitmap = (Bitmap) param.args[0];
            Log.i(TAG,param.method.getName() + " imageView,width=" + imageView.getWidth() + " height=" + imageView.getHeight()
                    + " bitmap,width=" + bitmap.getWidth() + " height=" + bitmap.getHeight());
        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
        }
    }

    static class ThreadMethodHook extends XC_MethodHook {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
            Thread t = (Thread) param.thisObject;
            Log.i(TAG, "thread:" + t + ", started..");
        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
            Thread t = (Thread) param.thisObject;
            Log.i(TAG, "thread:" + t + ", exit..");
        }
    }

    public static void start(){
        try {
            DexposedBridge.hookAllConstructors(ImageView.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    DexposedBridge.findAndHookMethod(ImageView.class, "setImageBitmap", Bitmap.class, new ImageViewMethodHook());
                }
            });

            DexposedBridge.hookAllConstructors(Thread.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Thread thread = (Thread) param.thisObject;
                    Class<?> clazz = thread.getClass();
                    if (clazz != Thread.class) {
                        Log.i(TAG, "found class extend Thread:" + clazz);
                        DexposedBridge.findAndHookMethod(clazz, "run", new ThreadMethodHook());
                    }
                    Log.i(TAG, "Thread: " + thread.getName() + " class:" + thread.getClass() + " is created.");
                }
            });
            //监控Thread
            DexposedBridge.findAndHookMethod(Thread.class, "run", new ThreadMethodHook());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //监控IPC
        try {
            DexposedBridge.findAndHookMethod(Class.forName("android.os.BinderProxy"), "transact",
                    int.class, Parcel.class,Parcel.class,int.class,new XC_MethodHook() {
                long time = 0;
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    time = System.currentTimeMillis();
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    String name = param.thisObject.getClass().getSimpleName();
                    long cost = System.currentTimeMillis() - time;
                    if (cost >= IPC_TIME_OUT){
                        StringBuilder builder = new StringBuilder();
                        builder.append("IPC,")
                                .append(name)
                                .append(" cost=")
                                .append(cost)
                                .append(" ms\n")
                                .append(Log.getStackTraceString(new Throwable()));
                        Log.w(TAG,builder.toString());
                    }
                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
