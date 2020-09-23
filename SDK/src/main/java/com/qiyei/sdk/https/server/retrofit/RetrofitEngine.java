package com.qiyei.sdk.https.server.retrofit;


import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.qiyei.sdk.https.HTTP;
import com.qiyei.sdk.https.api.HTTPException;
import com.qiyei.sdk.https.server.HttpCallManager;
import com.qiyei.sdk.https.server.HttpResponse;
import com.qiyei.sdk.https.server.HttpUtil;
import com.qiyei.sdk.https.server.IHttpCallback;
import com.qiyei.sdk.https.server.IHttpEngine;
import com.qiyei.sdk.https.server.HttpTask;
import com.qiyei.sdk.https.server.IHttpTransferCallback;
import com.qiyei.sdk.https.server.okhttp.MyInterceptor;
import com.qiyei.sdk.https.server.ProgressResponseBody;
import com.qiyei.sdk.https.server.okhttp.ProgressInterceptor;
import com.qiyei.sdk.log.LogManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Streaming;

/**
 * @author Created by qiyei2015 on 2017/10/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class RetrofitEngine implements IHttpEngine {

    /**
     * 主线程的Handler
     */
    private Handler mHandler = null;

    public RetrofitEngine() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public <T, R> void enqueueGetCall(final HttpTask<T> task, final IHttpCallback<R> callback) {

        Object params = HttpUtil.gsonToGetParams(task.getRequest());

        //构造Call
        Call call = buildCall(task,params);
        if (call == null){
            onFailureBuildCall(task,callback);
            return ;
        }
        //设置task到okHttp拦截器中
        setOkHttpInterceptorTag(call,task);
        //将任务加到队列里面
        HttpCallManager.getInstance().addCall(task.getTaskId(),call);

        //发起请求
        call.enqueue(new Callback<R>() {
            @Override
            public void onResponse(Call<R> call, Response<R> response) {
                //移除task
                HttpCallManager.getInstance().removeCall(task.getTaskId());

                HttpResponse<R> obj = new HttpResponse<>(response.body());
                callback.onSuccess(obj);
            }

            @Override
            public void onFailure(Call<R> call, Throwable t) {
                //移除task
                HttpCallManager.getInstance().removeCall(task.getTaskId());

                callback.onFailure((Exception) t);
            }
        });

    }

    @Override
    public <T, R> void enqueuePostCall(final HttpTask<T> task, final IHttpCallback<R> callback) {
        //获取task要执行的方法的参数
        Object params = task.getRequest().getBody();
        //构造Call
        Call call = buildCall(task,params);
        if (call == null){
            onFailureBuildCall(task,callback);
            return ;
        }
        //设置task到okHttp拦截器中
        setOkHttpInterceptorTag(call,task);
        //将任务加到队列里面
        HttpCallManager.getInstance().addCall(task.getTaskId(),call);

        call.enqueue(new Callback<R>() {
            @Override
            public void onResponse(Call<R> call, Response<R> response) {
                //移除task
                HttpCallManager.getInstance().removeCall(task.getTaskId());

                HttpResponse<R> obj = new HttpResponse<>(response.body());
                callback.onSuccess(obj);
            }

            @Override
            public void onFailure(Call<R> call, Throwable t) {
                //移除task
                HttpCallManager.getInstance().removeCall(task.getTaskId());

                callback.onFailure((Exception) t);
            }
        });
    }

    @Override
    public <T, R> void enqueueDownloadCall(final HttpTask<T> task, final IHttpTransferCallback<R> callback) {

        Retrofit retrofit = RetrofitFactory.createRetrofit(task.getRequest().getBaseUrl());
        //获取到OkHttpClient
        OkHttpClient client = (OkHttpClient) retrofit.callFactory();

        //找到Interceptor
        ProgressInterceptor interceptor = new ProgressInterceptor();
        interceptor.setProgressResponseBody(new ProgressResponseBody(callback));
        client = client.newBuilder().addInterceptor(interceptor).build();

        //改变okHttpClient，向其中添加拦截器
        Retrofit.Builder newBuilder = retrofit.newBuilder();
        newBuilder.client(client);
        retrofit = newBuilder.build();

//        try {
//            Field callFactoryField = retrofit.getClass().getDeclaredField("callFactory");
//            if (callFactoryField == null) {
//                LogManager.i(HTTP.TAG, "callFactoryField is null");
//                return;
//            }
//            callFactoryField.setAccessible(true);
//            callFactoryField.set(retrofit,client);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }

        //获取task要执行的方法的参数
        Object params = task.getRequest().getBody();

        //构造Call
        Call call = buildCall(retrofit,task,params);
        if (call == null){
            onFailureBuildCall(task,callback);
            return ;
        }
        //设置task到okHttp拦截器中
        setOkHttpInterceptorTag(call,task);

        //将任务加到队列里面
        HttpCallManager.getInstance().addCall(task.getTaskId(),call);

        call.enqueue(new Callback<R>() {
            @Override
            public void onResponse(Call<R> call, final Response<R> response) {
                //可以在这里构造ProgressResponseBody来实现进度的监听
                final ResponseBody responseBody = (ResponseBody) response.body();
//                final ProgressResponseBody responseBody = new ProgressResponseBody((ResponseBody) response.body(),callback);
//                new Thread(){
//                    @Override
//                    public void run() {
//
//                        //read the body to file
//                        BufferedSource source = responseBody.source();
//                        File outFile = new File(task.getRequest().getFilePath());
//                        outFile.delete();
//                        outFile.getParentFile().mkdirs();
//                        try {
//                            outFile.createNewFile();
//                            BufferedSink sink = Okio.buffer(Okio.sink(outFile));
//                            source.readAll(sink);
//                            sink.flush();
//                            source.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }.start();

                new Thread(){
                    @Override
                    public void run() {
                        try {
                            InputStream is = responseBody.byteStream();
                            File file = new File(task.getRequest().getFilePath());
                            //创建父目录
                            file.getParentFile().mkdirs();
                            if (file.exists()){
                                file.delete();
                            }
                            FileOutputStream fos = new FileOutputStream(file);
                            BufferedInputStream bis = new BufferedInputStream(is);
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = bis.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                                fos.flush();
                            }
                            fos.close();
                            bis.close();
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                //移除task
                                HttpCallManager.getInstance().removeCall(task.getTaskId());
                                HttpResponse<R> obj = new HttpResponse<>(response.body());
                                callback.onSuccess(obj);
                            }
                        });
                    }
                }.start();
            }

            @Override
            public void onFailure(Call<R> call, Throwable t) {
                //移除task
                HttpCallManager.getInstance().removeCall(task.getTaskId());

                callback.onFailure((Exception) t);
            }
        });
    }

    @Override
    public <T, R> void enqueueUploadCall(HttpTask<T> task, IHttpTransferCallback<R> callback) {


    }

    @Override
    public void cancelHttpCall(String taskId) {
        Object object = HttpCallManager.getInstance().queryCall(taskId);
        if (object == null){
            return;
        }

        if (object instanceof Call){
            Call call = (Call) object;
            if (call != null && !call.isCanceled()){
                call.cancel();
            }
        }
    }

    /**
     * @return {@link #mHandler}
     */
    @Override
    public Handler getHandler() {
        return mHandler;
    }

    /**
     * 设置OkHttp拦截器的Tag
     * @param call
     * @param task
     * @param <T>
     */
    private <T> void setOkHttpInterceptorTag(Call call,HttpTask<T> task){
        //获取OkHttp的request
        Request request = call.request();
        //反射设置 tag
        Class<?> clazz = request.getClass();
        try {
            Field field = clazz.getDeclaredField("tags");
            field.setAccessible(true);
            //将task设置成tag字段，保存数据
            Map<Class<?>,Object> originalMap = (Map<Class<?>, Object>) field.get(request);
            Map<Class<?>,Object> newMap = new HashMap<>();
            for (Map.Entry<Class<?>,Object> entry : originalMap.entrySet()){
                newMap.put(entry.getKey(),entry.getValue());
            }
            newMap.put(HttpTask.class,task);
            field.set(request,newMap);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 构造Call
     * @param task
     * @param params
     * @param <T>
     * @return
     */
    private <T> Call buildCall(HttpTask<T> task,Object params){
        Retrofit retrofit = RetrofitFactory.createRetrofit(task.getRequest().getBaseUrl());
        //根据Class找到要执行的RetrofitApiService接口
        Object apiService = retrofit.create(task.getRequest().getApiClazz());

        //获取到要执行的方法
        String methodName = getMethodName(task);

        if (TextUtils.isEmpty(methodName)){
            LogManager.i(HTTP.TAG,"cannot find method in " + task.getRequest().getApiClazz());
            return null;
        }

        Call call = null;
        try {
            Class<?> paramsClazz;
            if (params != null){
                //有参方法
                paramsClazz = getParamsClazz(apiService.getClass(),params.getClass(),methodName);
            }else {
                //无参方法
                paramsClazz = null;
            }

            Method method;
            if (paramsClazz == null){
                method = apiService.getClass().getDeclaredMethod(methodName,new Class[0]);
//                params = new Object[0];
            }else {
                method = apiService.getClass().getDeclaredMethod(methodName,new Class[]{paramsClazz});
            }

            LogManager.i(HTTP.TAG,"params:" + params);

            if (method != null){
                if (params != null){
                    call = (Call) method.invoke(apiService,params);
                }else {
                    call = (Call) method.invoke(apiService,new Object[0]);
                }
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return call;
    }

    /**
     *
     * @param retrofit
     * @param task
     * @param params
     * @param <T>
     * @return
     */
    private <T> Call buildCall(Retrofit retrofit,HttpTask<T> task,Object params){
        if (retrofit == null){
            retrofit = RetrofitFactory.createRetrofit(task.getRequest().getBaseUrl());
        }

        //根据Class找到要执行的RetrofitApiService接口
        Object apiService = retrofit.create(task.getRequest().getApiClazz());

        //获取到要执行的方法
        String methodName = getMethodName(task);

        if (TextUtils.isEmpty(methodName)){
            LogManager.i(HTTP.TAG,"cannot find method in " + task.getRequest().getApiClazz());
            return null;
        }

        Call call = null;
        try {
            Class<?> paramsClazz;
            if (params != null){
                //有参方法
                paramsClazz = getParamsClazz(apiService.getClass(),params.getClass(),methodName);
            }else {
                //无参方法
                paramsClazz = null;
            }

            Method method;
            if (paramsClazz == null){
                method = apiService.getClass().getDeclaredMethod(methodName,new Class[0]);
//                params = new Object[0];
            }else {
                method = apiService.getClass().getDeclaredMethod(methodName,new Class[]{paramsClazz});
            }

            LogManager.i(HTTP.TAG,"params:" + params);

            if (method != null){
                if (params != null){
                    call = (Call) method.invoke(apiService,params);
                }else {
                    call = (Call) method.invoke(apiService,new Object[0]);
                }
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return call;
    }

    /**
     * 找到method
     * @param task
     * @return
     */
    private String getMethodName(HttpTask task){
        String methodName = null;

        //这个循环找到method
        for (Method method : task.getRequest().getApiClazz().getDeclaredMethods()){

            String value = null;
            LogManager.d(HTTP.TAG,"taskId=" + task.getTaskId() + " getMethodName method=" + method.getName() + " from class " + task.getRequest().getApiClazz().getCanonicalName());
            switch (task.getRequest().getMethod()){
                case HTTP.GET:
                    GET getAnnotation = method.getAnnotation(GET.class);
                    if (getAnnotation != null){
                        value = getAnnotation.value();
                    }
                    break;

                case HTTP.POST:
                    POST postAnnotation = method.getAnnotation(POST.class);
                    if (postAnnotation != null){
                        value = postAnnotation.value();
                    }
                    break;

                case HTTP.DOWNLOAD:
                    if (method.getAnnotation(Streaming.class) != null){
                        GET downloadAnnotation = method.getAnnotation(GET.class);
                        if (downloadAnnotation != null){
                            value = downloadAnnotation.value();
                        }
                    }
                    break;

                case HTTP.UPLOAD:
                    if (method.getAnnotation(Multipart.class) != null){
                        POST uploadAnnotation = method.getAnnotation(POST.class);
                        if (uploadAnnotation != null){
                            value = uploadAnnotation.value();
                        }
                    }

                    break;

                default:
                    break;
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

    @Override
    public String toString() {
        return "RetrofitEngine";
    }


//    private void ttt(){
//        try {
//            Field callFactoryField = retrofit.getClass().getDeclaredField("callFactory");
//            if (callFactoryField == null){
//                LogManager.i(HTTP.TAG,"callFactoryField is null");
//                return;
//            }
//            callFactoryField.setAccessible(true);
//            //找到Client
//            OkHttpClient client = (OkHttpClient) callFactoryField.get(retrofit);
//            if (client == null){
//                LogManager.i(HTTP.TAG,"OkHttpClient is null");
//                return;
//            }
//            Field interceptorsField = client.getClass().getDeclaredField("interceptors");
//            if (interceptorsField == null){
//                LogManager.i(HTTP.TAG,"interceptorsField is null");
//                return;
//            }
//            interceptorsField.setAccessible(true);
//            //找到Interceptor
//            List<Interceptor> interceptorList = (List<Interceptor>) interceptorsField.get(client);
//
//            if (interceptorList == null || interceptorList.size() <= 0){
//                LogManager.i(HTTP.TAG,"interceptorList is null or size is 0");
//                return;
//            }
//            //找到Interceptor
//            MyInterceptor interceptor = (MyInterceptor) interceptorList.get(0);
//            if (interceptor == null){
//                LogManager.i(HTTP.TAG,"MyInterceptor is null ");
//                return;
//            }
//            interceptor.setProgressResponseBody(new ProgressResponseBody(callback));
//            //interceptorList.add(0,interceptor);
//
//            //设置回去
//            interceptorsField.set(client,interceptorList);
//            callFactoryField.set(retrofit,client);
//
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 先自行
     */
//    private void test2(){
//        test(IRetrofitService.class,"getDiscoverList","discovery/v3/",Map.class);
//
//        //获取task要执行的方法的参数
//        Map<String,String> params = HttpUtil.gsonToGetParams(task.getRequest());
//
//        Retrofit retrofit = RetrofitFactory.createRetrofit(task.getRequest().getBaseUrl());
//
//        //构造Call
//        IRetrofitService service = retrofit.create(IRetrofitService.class);
//        Call<Object> call = service.getDiscoverList(params);
//
//        if (call == null){
//            return ;
//        }
//        //设置task到okHttp拦截器中
//        setOkHttpInterceptorTag(call,task);
//        //将任务加到队列里面
//        HttpCallManager.getInstance().addCall(task.getTaskId(),call);
//
//        call.enqueue(new Callback<Object>() {
//
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response) {
//                //移除task
//                HttpCallManager.getInstance().removeCall(task.getTaskId());
//
//                HttpResponse<R> obj = new HttpResponse<>((R)response.body());
//                callback.onSuccess(obj);
//            }
//
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                //移除task
//                HttpCallManager.getInstance().removeCall(task.getTaskId());
//
//                callback.onFailure((Exception) t);
//            }
//        });
//    }

    private void test(Class<?> clazz,String methodName,String annotationValue,Class<?>... parameterTypes){

        try {
            Method method = clazz.getDeclaredMethod(methodName,parameterTypes);
            if (method == null){
                return;
            }
            method.setAccessible(true);

            GET getAnnotation = method.getAnnotation(GET.class);
            if (getAnnotation == null){
                return;
            }

            //获取这个代理的InvocationHandler,这里的Handler是AnnotationFactory libcore.reflect.AnnotationFactory
            InvocationHandler handler = Proxy.getInvocationHandler(getAnnotation);
            if (handler == null){
                return;
            }
            for (Field field : handler.getClass().getDeclaredFields()){
                LogManager.i(HTTP.TAG,"field:" + field.getName() + "  type:" + field.getType().getName());
            }

            Field field = handler.getClass().getDeclaredField("elements");
            if (field == null){
                return;
            }
            field.setAccessible(true);

            //是AnnotationMember[] 数组
            Object objects = field.get(handler);
            Class<?> type = objects.getClass();
            if (type.isArray()){
                LogManager.i(HTTP.TAG,"length:" + Array.getLength(objects));
                Object object = Array.get(objects,0);
                if (object == null){
                    return;
                }
                Class<?> annotationMemberClazz = object.getClass();
                Field valueField = annotationMemberClazz.getDeclaredField("value");
                if (valueField == null){
                    return;
                }
                valueField.setAccessible(true);
                valueField.set(object,annotationValue);

                //数组设置回去
                Array.set(objects,0,object);
                LogManager.i(HTTP.TAG,"annotationValue:" + annotationValue);

                field.set(handler,objects);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private <T,R> void onFailureCall(HttpTask<T> task, final IHttpCallback<R> callback,Exception exception){
        //移除task
        HttpCallManager.getInstance().removeCall(task.getTaskId());
        callback.onFailure(exception);
    }

    private <T,R> void onFailureBuildCall(HttpTask<T> task, final IHttpCallback<R> callback){
        onFailureCall(task,callback,new HTTPException(task.getTaskId(),"build call exception ,result is null"));
    }
}
