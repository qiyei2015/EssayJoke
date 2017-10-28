package com.qiyei.sdk.https.server;

import android.content.Context;

import android.support.v4.app.FragmentManager;

import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.https.IHttpExecutor;
import com.qiyei.sdk.https.api.IHttpListener;
import com.qiyei.sdk.https.api.HttpRequest;
import com.qiyei.sdk.https.HTTP;
import com.qiyei.sdk.https.server.okhttp.OkHttpEngine;
import com.qiyei.sdk.https.server.retrofit.RetrofitEngine;
import com.qiyei.sdk.log.LogManager;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: http服务，处理http请求
 */
public class HttpServer implements IHttpExecutor{

    /**
     * Https引擎，http请求执行者
     */
    private IHttpEngine mEngine;

    /**
     * Http请求管理者
     */
    private HttpCallManager mCallManager;

    /**
     * Http构造方法
     * @param context
     */
    private HttpServer(Context context,IHttpEngine engine) {
        mEngine = engine;
        onCreate();
    }

    /**
     * 静态内部类
     */
    private static class SingleHolder{
        private final static HttpServer sServer = new HttpServer(RuntimeEnv.appContext,new RetrofitEngine());
    }

    /**
     * 获取默认的HttpServer
     * @return
     */
    public static HttpServer getDefault(){
        return SingleHolder.sServer;
    }

    /**
     * 创建函数
     */
    public void onCreate(){
        LogManager.i(HTTP.TAG,"HttpServer created !");
    }

    /**
     * 销毁函数
     */
    public void onDestory(){
        //做一些清理工作
    }

    /**
     * 设置引擎
     * @param engine
     */
    public void setEngine(IHttpEngine engine){
        mEngine = engine;
    }

    @Override
    public <T,R> String execute(HttpRequest<T> request, IHttpListener<R> listener) {
        return execute((FragmentManager) null,request,listener);
    }

    @Override
    public <T,R> String execute(final FragmentManager fragmentManager, HttpRequest<T> request, final IHttpListener<R> listener) {
        String taskId = null;

        HttpTask<T> task = new HttpTask<T>(request.getMethod(),request, listener);

        taskId = task.getTaskId();

        mEngine.execute(fragmentManager, task, new IHttpCallback<R>() {

            @Override
            public void onSuccess(HttpResponse<R> response) {
                if (HttpResponse.isOK(response)){
                    listener.onSuccess(response.getContent());
                }else {
                    listener.onFailure(new Exception("is not ok"));
                }
            }

            @Override
            public void onFailure(Exception exception) {
                LogManager.i(HTTP.TAG,"exception:" + exception.toString());
                listener.onFailure(exception);
            }
        });

        return taskId;
    }

    @Override
    public <T,R> String execute(android.app.FragmentManager fragmentManager, HttpRequest<T> request, IHttpListener<R> listener) {
        return null;
    }

    @Override
    public void cancel(String taskId) {
        mEngine.cancelHttpCall(taskId);
    }

}
