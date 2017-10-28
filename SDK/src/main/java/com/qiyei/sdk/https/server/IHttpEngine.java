package com.qiyei.sdk.https.server;


import android.support.v4.app.FragmentManager;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: Https引擎
 */
public interface IHttpEngine {

    /**
     * get请求
     * @param fragmentManager
     * @param task
     * @param callback
     * @return 返回task id
     */
    <T,R> String execute(final FragmentManager fragmentManager, final HttpTask<T> task, final IHttpCallback<R> callback);


    /**
     * post请求
     * @param fragmentManager
     * @param task
     * @param callback
     * @return 返回task id
     */
    <T,R> String execute(final android.app.FragmentManager fragmentManager, final HttpTask<T> task, final IHttpCallback<R> callback);

    /**
     * 取消http请求
     * @param taskId
     */
    void cancelHttpCall(String taskId);

}
