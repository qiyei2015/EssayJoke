package com.qiyei.sdk.https.server;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: http内部回调
 */
public interface IHttpCallback<R>{

    /**
     * 成功的回调
     * @param response
     */
    void onSuccess(HttpResponse<R> response);

    /**
     * 失败的回调
     * @param exception
     */
    void onFailure(Exception exception);

}
