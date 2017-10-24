package com.qiyei.sdk.https.server.retrofit;

import android.support.v4.app.FragmentManager;

import com.qiyei.sdk.https.api.request.HttpDownloadRequest;
import com.qiyei.sdk.https.api.request.HttpGetRequest;
import com.qiyei.sdk.https.api.request.HttpPostRequest;
import com.qiyei.sdk.https.api.request.HttpUploadRequest;
import com.qiyei.sdk.https.base.Https;
import com.qiyei.sdk.https.server.HttpCallManager;
import com.qiyei.sdk.https.server.HttpResponse;
import com.qiyei.sdk.https.server.IHttpCallback;
import com.qiyei.sdk.https.server.IHttpEngine;
import com.qiyei.sdk.https.server.IHttpTransferCallback;
import com.qiyei.sdk.https.server.okhttp.OkHttpHelper;
import com.qiyei.sdk.https.server.task.HttpGetTask;
import com.qiyei.sdk.log.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
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


        //进行参数的注入
        //setPath(null,task.getRequest().getPathUrl());

        // TODO: 2017/10/24 这里建议通过反射构造Call请求 ，task.request中含有ApiService的class就可以了，通过pathUrl来调用方法
        Call call = task.getRequest().getCall();

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

}
