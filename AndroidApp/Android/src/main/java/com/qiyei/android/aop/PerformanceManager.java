package com.qiyei.android.aop;

import com.qiyei.sdk.log.LogManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author Created by qiyei2015 on 2019/11/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Aspect
public class PerformanceManager {

    private static final String TAG = "PerformanceManager";

    @Around("execution(* com.qiyei.android.AndroidApplication.on**(..))")
    public Object getAndroidApplicationTime(ProceedingJoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        long time = System.currentTimeMillis();
        Object object = null;
        try {
            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        LogManager.i(TAG, "getAndroidApplicationTime "+ signature.getName() + " cost " + (System.currentTimeMillis() - time) + " ms");
        return object;
    }

    @Around("call(* com.qiyei.android.AndroidApplication.**(..))")
    public Object getApplicationTime(ProceedingJoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        long time = System.currentTimeMillis();
        Object object = null;
        try {
            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        LogManager.i(TAG, "getApplicationTime " + signature.getName() + " cost " + (System.currentTimeMillis() - time) + " ms");

        return object;
    }


}
