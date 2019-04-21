package com.qiyei.mall.performance;

import com.qiyei.sdk.log.LogManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author Created by qiyei2015 on 2019/4/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Aspect
public class PerformanceManager {

    private static final String TAG = "PerformanceManager";

    @Around("execution(* com.qiyei.mall.MallApplication.on**(..))")
    public void getTime(ProceedingJoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        long time = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        LogManager.i(TAG, signature.getName() + " cost " + (System.currentTimeMillis() - time) + " ms");
    }

//    @Around("execution(* com.qiyei.sdk.SDKManager.**(..))")
//    public void getTime2(ProceedingJoinPoint joinPoint){
//        Signature signature = joinPoint.getSignature();
//        long time = System.currentTimeMillis();
//        try {
//            joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        LogManager.i(TAG, signature.getName() + " cost " + (System.currentTimeMillis() - time) + " ms");
//    }
}
