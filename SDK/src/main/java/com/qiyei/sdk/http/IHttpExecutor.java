package com.qiyei.sdk.http;

import android.support.v4.app.FragmentManager;

import com.qiyei.sdk.http.base.HttpRequest;
import com.qiyei.sdk.http.base.INetCallback;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/10/19.
 * Version: 1.0
 * Description:
 */
public interface IHttpExecutor {

    /**
     * 执行https请求
     * @param request
     * @param callback
     * @param <T>
     * @return
     */
    <T> String execute(HttpRequest request, final INetCallback<T> callback);

    /**
     * 执行https请求
     * @param fragmentManager
     * @param request
     * @param callback
     * @param <T>
     * @return
     */
    <T> String execute(FragmentManager fragmentManager, HttpRequest request, final INetCallback<T> callback);

    /**
     * 取消网络请求
     * @param taskId 需要取消的 id
     */
    void cancel(String taskId);

}
