package com.qiyei.sdk.https.server;

import android.content.Context;

import android.support.v4.app.FragmentManager;

import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.http.HttpUtil;
import com.qiyei.sdk.https.IHttpExecutor;
import com.qiyei.sdk.https.api.listener.IHttpListener;
import com.qiyei.sdk.https.api.request.HttpGetRequest;
import com.qiyei.sdk.https.api.request.HttpRequest;
import com.qiyei.sdk.https.base.Https;
import com.qiyei.sdk.https.server.okhttp.OkHttpEngine;
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
        LogManager.i(Https.TAG,"HttpServer created !");
    }

    /**
     * 静态内部类
     */
    private static class SingleHolder{
        private final static HttpServer sServer = new HttpServer(RuntimeEnv.appContext,new OkHttpEngine());
    }

    /**
     * 获取默认的HttpServer
     * @return
     */
    public static HttpServer getDefault(){
        return SingleHolder.sServer;
    }

    /**
     * 设置引擎
     * @param engine
     */
    public void setEngine(IHttpEngine engine){
        mEngine = engine;
    }

    @Override
    public <T> String execute(HttpRequest request, IHttpListener<T> listener) {
        return null;
    }

    @Override
    public <T> String execute(final FragmentManager fragmentManager, HttpRequest request, final IHttpListener<T> listener) {
        String taskId = null;
        if (request instanceof HttpGetRequest){
            taskId= mEngine.get(fragmentManager,(HttpGetRequest) request, new IHttpCallback() {
                @Override
                public void onSuccess(String response) {
                    LogManager.i(Https.TAG,"response:" + response);
                    final T obj = (T) HttpUtil.parseJson(response,listener.getClass());
                    listener.onSuccess(obj);
                }

                @Override
                public void onFailure(Exception exception) {
                    LogManager.i(Https.TAG,"exception:" + exception.toString());
                    listener.onFailure(exception);
                }
            });
        }

        return taskId;
    }

    @Override
    public <T> String execute(android.app.FragmentManager fragmentManager, HttpRequest request, IHttpListener<T> listener) {
        return null;
    }

    @Override
    public void cancel(String taskId) {

    }

}
