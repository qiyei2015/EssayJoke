package com.qiyei.sdk.permission;

import com.qiyei.sdk.log.LogManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/9/25.
 * Version: 1.0
 * Description: 权限处理器
 */
public class PermissionProcessor {

    /**
     * 权限调用成功时
     * @param object
     * @param requestCode
     */
    public static void requestPermissionSuccess(Object object,int requestCode){
        //反射获取所有方法
        Method[] methods = object.getClass().getDeclaredMethods();

        if (methods == null){
            LogManager.w(PermissionConstant.TAG,"methods is null");
            return;
        }

        //逐一找到被注解的方法
        for (Method method : methods){
            PermissionSuccess success = method.getAnnotation(PermissionSuccess.class);
            if (success != null){
                int code = success.requestCode();
                if (code == requestCode){
                    LogManager.i(PermissionConstant.TAG,"PermissionSuccess method :" + method.toString());
                    executeMethod(object,method);
                }
            }
        }
    }

    /**
     * 权限调用失败时
     * @param object
     * @param requestCode
     */
    public static void requestPermissionFail(Object object,int requestCode){
        //反射获取所有方法
        Method[] methods = object.getClass().getDeclaredMethods();

        if (methods == null){
            LogManager.w(PermissionConstant.TAG,"methods is null");
            return;
        }

        //逐一找到被注解的方法
        for (Method method : methods){
            PermissionFail fail = method.getAnnotation(PermissionFail.class);
            if (fail != null){
                int code = fail.requestCode();
                if (code == requestCode){
                    LogManager.i(PermissionConstant.TAG,"PermissionFail method :" + method.toString());
                    executeMethod(object,method);
                }
            }
        }
    }


    /**
     * 反射执行该方法,因为找到了该方法，需要执行该方法
     * @param object
     * @param method
     */
    private static void executeMethod(Object object,Method method){
        try {
            method.setAccessible(true);
            method.invoke(object,new Object[]{});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
