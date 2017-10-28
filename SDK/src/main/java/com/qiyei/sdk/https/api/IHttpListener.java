package com.qiyei.sdk.https.api;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: http请求监听器
 */
public interface IHttpListener<T> {

    /**
     * 成功的回调
     * @param response
     */
    void onSuccess(T response);

    /**
     * 失败的回调
     * @param exception
     */
    void onFailure(Exception exception);
}
