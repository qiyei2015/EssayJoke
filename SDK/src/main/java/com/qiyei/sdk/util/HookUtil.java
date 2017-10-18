package com.qiyei.sdk.util;

import android.content.Context;
import android.content.Intent;

import com.qiyei.sdk.log.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/10/18.
 * Version: 1.0
 * Description:
 */
public class HookUtil {

    private static final String TAG = HookUtil.class.getSimpleName();

    private static Context sContext;

    private static Class<?> sClazz;

    private static final String EXTRA = "extra";


    public static void hook(Context context,Class<?> clazz) throws Exception{

        sContext = context.getApplicationContext();
        sClazz = clazz;

        Class<?> iActivityManagerClazz = Class.forName("android.app.IActivityManager");

        Class<?> activityManagerNativeClazz = Class.forName("android.app.ActivityManagerNative");
        Field gDefaultField = activityManagerNativeClazz.getDeclaredField("gDefault");
        gDefaultField.setAccessible(true);
        //获取到ActivityManagerNative 中的gDefault android8.0 中
        Object gDefault = gDefaultField.get(null);

        //获取gDefault中的mInstance
        Class<?>  singletonClazz = Class.forName("android.util.Singleton");
        Field instanceField = singletonClazz.getDeclaredField("mInstance");
        instanceField.setAccessible(true);
        Object instance = instanceField.get(gDefault);

        Object proxy = Proxy.newProxyInstance(HookUtil.class.getClassLoader(), new Class[]{iActivityManagerClazz},new ClassInvocationHandler(instance));

        //设置代理对象到原来的gDefault中的mInstance中去
        instanceField.set(gDefault,proxy);

    }

    private static class ClassInvocationHandler implements InvocationHandler{

        /**
         * 要代理的对象，暂时对象
         */
        private Object mTarget;

        public ClassInvocationHandler(Object object){
            mTarget = object;
        }

        /**
         *
         * @param proxy 代理对象
         * @param method 代理的对象要执行的方法
         * @param args 代理的对象的方法的参数
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            LogManager.i(TAG,"method:" + method.getName());

            if ("startActivity".equals(method.getName())){
                Intent originIntent = (Intent) args[2];

                Intent newIntent = new Intent(sContext,sClazz);
                newIntent.putExtra(EXTRA,originIntent);
                args[2] = newIntent;
            }
            //反射到真实对象上去执行方法
            return method.invoke(mTarget,args);
        }
    }



}
