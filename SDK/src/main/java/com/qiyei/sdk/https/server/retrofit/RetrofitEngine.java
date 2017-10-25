package com.qiyei.sdk.https.server.retrofit;

import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.qiyei.sdk.https.base.Http;
import com.qiyei.sdk.https.dialog.LoadingDialog;
import com.qiyei.sdk.https.dialog.LoadingManager;
import com.qiyei.sdk.https.server.HttpCallManager;
import com.qiyei.sdk.https.server.HttpResponse;
import com.qiyei.sdk.https.server.HttpUtil;
import com.qiyei.sdk.https.server.IHttpCallback;
import com.qiyei.sdk.https.server.IHttpEngine;
import com.qiyei.sdk.https.server.okhttp.OkHttpHelper;
import com.qiyei.sdk.https.server.task.HttpGetTask;
import com.qiyei.sdk.log.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
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
                LogManager.i(Http.TAG,"pathUrl:" + task.getRequest().getPathUrl());
                LogManager.i(Http.TAG,"value():" + getAnnotation.value());
                //通过pathUrl与GET注解来找到，如果相等就返回
                if (task.getRequest().getPathUrl().equals(getAnnotation.value())){
                    methodName = method.getName();
                    LogManager.i(Http.TAG,"method :" + methodName);
                }
            }
        }
        if (TextUtils.isEmpty(methodName)){
            LogManager.i(Http.TAG,"cannot find method in " + task.getRequest().getApiClazz());
            return null;
        }

        Call call = null;

        LogManager.i(Http.TAG,"get: 1");
        Map<String,String> params = HttpUtil.gsonToGetParams(task.getRequest());
        LogManager.i(Http.TAG,"paramsClazz "+ params.getClass() + " params:" + params.toString());
        try {
            Class<?> paramsClazz = getParamsClazz(apiService.getClass(),params.getClass(),methodName);

            Method method;
            if (paramsClazz == null){
                method = apiService.getClass().getDeclaredMethod(methodName,new Class[0]);
                if (method != null){
                    call = (Call) method.invoke(apiService,new Object[0]);
                }
            }else {
                method = apiService.getClass().getDeclaredMethod(methodName,new Class[]{paramsClazz});
                if (method != null){
                    call = (Call) method.invoke(apiService,params);
                }
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

        LoadingManager.showDialog(fragmentManager,task.getTaskId());

        //获取OkHttp的request
        Request request = call.request();
        //反射设置 tag
        Class<?> clazz = request.getClass();
        try {
            Field field = clazz.getDeclaredField("tag");
            field.setAccessible(true);
            field.set(request,task.getTaskId());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        call.enqueue(new Callback<R>() {
            @Override
            public void onResponse(Call<R> call, Response<R> response) {
                LoadingManager.dismissDialog(fragmentManager,task.getTaskId());
                LogManager.i(Http.TAG,"Retrofit --> " + response.body().toString());
                HttpResponse<R> obj = new HttpResponse<>(response.body());
                callback.onSuccess(obj);
            }

            @Override
            public void onFailure(Call<R> call, Throwable t) {
                LoadingManager.dismissDialog(fragmentManager,task.getTaskId());
                callback.onFailure((Exception) t);
            }
        });

        return task.getTaskId();
    }

    @Override
    public void cancelHttpCall(String taskId) {
        Call call = HttpCallManager.getInstance().queryCall(taskId,"");
        if (call != null && !call.isCanceled()){
            call.cancel();
        }
    }

    /**
     * 找到原始的接口的中的正确的参数类型
     * @param targetClazz
     * @param paramsClazz
     * @param name
     * @return
     */
    private Class<?> getParamsClazz(Class<?> targetClazz,Class<?> paramsClazz,String name){

        if (targetClazz == null || paramsClazz == null || TextUtils.isEmpty(name)){
            return null;
        }

        for (Method method : targetClazz.getDeclaredMethods()){
            //根据名字找到方法
            if (name.equals(method.getName())){
                LogManager.i(Http.TAG,"paramsClazz :" + paramsClazz.getName());
                //目前只考虑只有一个参数的
                Class<?>[] typeClazzs = method.getParameterTypes();

                //该方法是无参方法
                if (typeClazzs == null) {
                    return null;
                }
                if (typeClazzs != null && typeClazzs.length <= 0){
                    return null;
                }

                Class<?> clazz = paramsClazz;

                List<Class<?>> interfaces = new ArrayList<>();
                if (clazz.getInterfaces() != null){
                    interfaces.addAll(Arrays.asList(clazz.getInterfaces()));
                }

                //现在本类及父类里面找
                while (clazz != null && !clazz.getName().equals(typeClazzs[0].getName())){
                    clazz = clazz.getSuperclass();
                    if (clazz != null && clazz.getInterfaces() != null){
                        interfaces.addAll(Arrays.asList(clazz.getInterfaces()));
                    }
                }

                //在父类里面找到
                if (clazz != null){
                    return clazz;
                }

                //在接口里面找
                for (Class<?> cla : interfaces){
                    //接口里面找到
                    if (cla.getName().equals(typeClazzs[0].getName())){
                        return cla;
                    }
                }
            }
        }
        return null;
    }
}
