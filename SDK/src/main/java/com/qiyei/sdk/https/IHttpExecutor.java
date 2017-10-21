package com.qiyei.sdk.https;

import android.support.v4.app.FragmentManager;


import com.qiyei.sdk.https.api.listener.IHttpListener;
import com.qiyei.sdk.https.api.request.HttpRequest;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface IHttpExecutor {

    /**
     * 执行https请求
     * @param request 请求参数
     * @param listener 回调listener
     * @param <T> 泛型参数
     * @return 该任务的taskId
     */
    <T> String execute(HttpRequest request, IHttpListener<T> listener);

    /**
     * 执行https请求
     * @param fragmentManager 显示对话框的fragment
     * @param request 请求参数
     * @param listener 回调listener
     * @param <T> 泛型参数
     * @return 该任务的taskId
     */
    <T> String execute(FragmentManager fragmentManager, HttpRequest request,IHttpListener<T> listener);

    /**
     * 执行https请求
     * @param fragmentManager 显示对话框的fragment
     * @param request 请求参数
     * @param listener 回调listener
     * @param <T> 泛型参数
     * @return 该任务的taskId
     */
    <T> String execute(android.app.FragmentManager fragmentManager, HttpRequest request,IHttpListener<T> listener);

    /**
     * 取消网络请求
     * @param taskId 需要取消的 taskId
     */
    void cancel(String taskId);
}
