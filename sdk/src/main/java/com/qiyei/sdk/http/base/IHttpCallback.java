package com.qiyei.sdk.http.base;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/29.
 * Version: 1.0
 * Description: http请求的回调
 */
public interface IHttpCallback {
    /**
     * 成功的回调
     * @param s
     */
    void onSuccess(String s);

    /**
     * 失败的回调
     * @param e
     */
    void onFail(Exception e);
}
