package com.qiyei.sdk.https.server;

import android.support.v4.app.FragmentManager;

import com.qiyei.sdk.https.api.listener.IHttpListener;
import com.qiyei.sdk.https.api.request.HttpRequest;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: httpServer的代理
 */
public class HttpServerProxy {

    private HttpServer mHttpServer;

    public HttpServerProxy(){
        mHttpServer = HttpServer.getDefault();
    }

    public <T> String execute(HttpRequest request, IHttpListener<T> listener) {
        return mHttpServer.execute(request,listener);
    }


    public <T> String execute(FragmentManager fragmentManager, HttpRequest request, IHttpListener<T> listener) {
        return mHttpServer.execute(fragmentManager,request,listener);
    }


    public <T> String execute(android.app.FragmentManager fragmentManager, HttpRequest request, IHttpListener<T> listener) {
        return mHttpServer.execute(fragmentManager,request,listener);
    }


    public void cancel(String taskId) {
        mHttpServer.cancel(taskId);
    }

}
