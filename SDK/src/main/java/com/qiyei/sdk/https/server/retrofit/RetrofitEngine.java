package com.qiyei.sdk.https.server.retrofit;

import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.qiyei.sdk.https.api.request.HttpDownloadRequest;
import com.qiyei.sdk.https.api.request.HttpGetRequest;
import com.qiyei.sdk.https.api.request.HttpPostRequest;
import com.qiyei.sdk.https.api.request.HttpUploadRequest;
import com.qiyei.sdk.https.base.Https;
import com.qiyei.sdk.https.server.HttpCallManager;
import com.qiyei.sdk.https.server.HttpResponse;
import com.qiyei.sdk.https.server.HttpUtil;
import com.qiyei.sdk.https.server.IHttpCallback;
import com.qiyei.sdk.https.server.IHttpEngine;
import com.qiyei.sdk.https.server.IHttpTransferCallback;
import com.qiyei.sdk.https.server.okhttp.OkHttpHelper;
import com.qiyei.sdk.https.server.task.HttpGetTask;
import com.qiyei.sdk.log.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * @author Created by qiyei2015 on 2017/10/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class RetrofitEngine implements IHttpEngine {


    @Override
    public <T,R> String get(final FragmentManager fragmentManager, final HttpGetTask<T> task, final IHttpCallback<R> callback) {
        Retrofit retrofit = RetrofitFactory.createRetrofit(task.getRequest().getBaseUrl());
        Object apiService = retrofit.create(task.getRequest().getApiClazz());
        String methodName = null;
        //这个循环找到method
        for (Method method : task.getRequest().getApiClazz().getDeclaredMethods()){
            //获取GET注解
            GET getAnnotation = method.getAnnotation(GET.class);
            if (getAnnotation != null){
                LogManager.i(Https.TAG,"pathUrl:" + task.getRequest().getPathUrl());
                LogManager.i(Https.TAG,"value():" + getAnnotation.value());
                //通过pathUrl与GET注解来找到，如果相等就返回
                if (task.getRequest().getPathUrl().equals(getAnnotation.value())){
                    methodName = method.getName();
                    LogManager.i(Https.TAG,"method :" + methodName);
                }
            }
        }
        if (TextUtils.isEmpty(methodName)){
            LogManager.i(Https.TAG,"cannot find method in " + task.getRequest().getApiClazz());
            return null;
        }

        Call call = null;

        LogManager.i(Https.TAG,"get: 1");
        Map<String,String> params = HttpUtil.gsonToGetParams(task.getRequest());
        LogManager.i(Https.TAG,"paramsClazz "+ params.getClass() + " params:" + params.toString());
        try {
            Class<?> paramsClazz = getParamsClazz(apiService.getClass(),params.getClass(),methodName);
            // TODO: 2017/10/25 需要根据父类，子类，以及接口找到类
            Method method = apiService.getClass().getDeclaredMethod(methodName,new Class[]{params.getClass().getSuperclass().getInterfaces()[0]});
            if (method != null){
                call = (Call) method.invoke(apiService,params);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (call == null){
            return null;
        }

        //将任务加到队列里面
        HttpCallManager.getInstance().addCall(task.getTaskId(),call);

        OkHttpHelper.showDialog(fragmentManager,task.getTaskId());

        call.enqueue(new Callback<R>() {
            @Override
            public void onResponse(Call<R> call, Response<R> response) {
                OkHttpHelper.dismissDialog(fragmentManager,task.getTaskId());
                LogManager.i(Https.TAG,"Retrofit --> " + response.body().toString());
                HttpResponse<R> obj = new HttpResponse<>(response.body());
                callback.onSuccess(obj);
            }

            @Override
            public void onFailure(Call<R> call, Throwable t) {
                OkHttpHelper.dismissDialog(fragmentManager,task.getTaskId());
                callback.onFailure((Exception) t);
            }
        });

        return task.getTaskId();
    }

    @Override
    public void cancelHttpCall(String taskId) {

    }

    /**
     * 设置参数的path
     * @param clazz
     * @param path
     */
    private void setPath(Class<?> clazz,String path){
        Class<?> retrofitServiceClazz = IRetrofitService.class;

        try {
            Method method = retrofitServiceClazz.getDeclaredMethod("createGetCall",new Class<?>[]{});

            if (method != null){
                method.setAccessible(true);
            }
            GET getAnnotation = method.getAnnotation(GET.class);
            if (getAnnotation != null){
                //获取AnnotationInvocationHandler
                InvocationHandler handler = Proxy.getInvocationHandler(getAnnotation);
                //获取获取AnnotationInvocationHandler的memberValues字段
//                Class<?> clazzAnnotationInvocationHandler = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
                Field memberValuesField = handler.getClass().getDeclaredField("memberValues");
                if (memberValuesField != null){
                    memberValuesField.setAccessible(true);
                    Map<String,Object> memberValues = (Map<String, Object>) memberValuesField.get(handler);
                    memberValues.put("values",path);
                    memberValuesField.set(handler,memberValues);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private void printClazzMethodInfo(Class<?> clazz){
        for (Method method : clazz.getDeclaredMethods()){
            LogManager.i(Https.TAG,"method: " + method.getName());
            printMethodParameterTypes(method.getParameterTypes());
        }
    }

    private void printMethodParameterTypes(Class<?>[] classes){
        for (Class<?> clazz : classes){
            LogManager.i(Https.TAG,"params clazz : " + clazz.getName());
        }
    }

    /**
     * 找到原始的接口的中的正确的参数类型
     * @param originalClazz
     * @param newClazz
     * @param name
     * @return
     */
    private Class<?> getParamsClazz(Class<?> originalClazz,Class<?> newClazz,String name){

        if (originalClazz == null || newClazz == null || TextUtils.isEmpty(name)){
            return null;
        }

        for (Method method : originalClazz.getDeclaredMethods()){
            //根据名字找到方法
            if (name.equals(method.getName())){
                LogManager.i(Https.TAG,"newClazz :" + newClazz.getName());
                Class<?>[] interfaceArray = newClazz.getInterfaces();
                printMethodParameterTypes(interfaceArray);
                for (Class<?> cla : method.getParameterTypes()){
                    if (cla == null){
                        continue;
                    }
                    for (Class<?> clazz : interfaceArray){
                        if (clazz.getName().equals(cla.getName())){
                            LogManager.i(Https.TAG,"getParamsClazz clazz name :" + clazz.getName());
                            return clazz;
                        }
                    }
//                    //循环找到子类
//                    while (clazz != null && !clazz.getName().equals(cla.getName())){
//                        clazz = clazz.getSuperclass();
//                    }
//                    LogManager.i(Https.TAG,"getParamsClazz clazz name :" + clazz.getName());
//                    return clazz;
                }
            }
        }
        return null;
    }
}
