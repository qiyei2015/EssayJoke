package com.qiyei.sdk.https.server.retrofit;

import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.qiyei.sdk.https.HTTP;
import com.qiyei.sdk.https.dialog.LoadingManager;
import com.qiyei.sdk.https.server.HttpCallManager;
import com.qiyei.sdk.https.server.HttpResponse;
import com.qiyei.sdk.https.server.HttpUtil;
import com.qiyei.sdk.https.server.IHttpCallback;
import com.qiyei.sdk.https.server.IHttpEngine;
import com.qiyei.sdk.https.server.HttpTask;
import com.qiyei.sdk.log.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author Created by qiyei2015 on 2017/10/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class RetrofitEngine implements IHttpEngine {

    @Override
    public <T,R> String execute(final FragmentManager fragmentManager, final HttpTask<T> task, final IHttpCallback<R> callback) {

        Retrofit retrofit = RetrofitFactory.createRetrofit(task.getRequest().getBaseUrl());
        Object apiService = retrofit.create(task.getRequest().getApiClazz());

        String methodName = getMethodName(task);

        if (TextUtils.isEmpty(methodName)){
            LogManager.i(HTTP.TAG,"cannot find method in " + task.getRequest().getApiClazz());
            return null;
        }

        Object params = null;

        if (task.getRequest().getMethod().equals(HTTP.GET)){
            params = HttpUtil.gsonToGetParams(task.getRequest());
        }else if (task.getRequest().getMethod().equals(HTTP.POST)){
            params = task.getRequest().getBody();
        }

        if (params == null){
            return null;
        }

        Call call = null;
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
            //将task设置成tag字段，保存数据
            field.set(request,task);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        call.enqueue(new Callback<R>() {
            @Override
            public void onResponse(Call<R> call, Response<R> response) {
                LoadingManager.dismissDialog(fragmentManager,task.getTaskId());
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
    public <T, R> String execute(android.app.FragmentManager fragmentManager, HttpTask<T> task, IHttpCallback<R> callback) {
        return null;
    }


    @Override
    public void cancelHttpCall(String taskId) {
        Call call = HttpCallManager.getInstance().queryCall(taskId,"");
        if (call != null && !call.isCanceled()){
            call.cancel();
        }
    }


    private String getMethodName(HttpTask task){
        String methodName = null;

        //这个循环找到method
        for (Method method : task.getRequest().getApiClazz().getDeclaredMethods()){

            String value = null;

            if (task.getRequest().getMethod().equals(HTTP.GET)){
                GET annotation = method.getAnnotation(GET.class);
                if (annotation != null){
                    value = annotation.value();
                }
            }else if (task.getRequest().getMethod().equals(HTTP.POST)){
                POST annotation = method.getAnnotation(POST.class);
                if (annotation != null){
                    value = annotation.value();
                }
            }

            if (value != null && task.getRequest().getPathUrl().equals(value)){
                methodName = method.getName();
                LogManager.v(HTTP.TAG,"method :" + methodName);
            }
        }

        return methodName;
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
                LogManager.v(HTTP.TAG,"paramsClazz :" + paramsClazz.getName());
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
