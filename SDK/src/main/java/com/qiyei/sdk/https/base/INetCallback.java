package com.qiyei.sdk.https.base;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/29.
 * Version: 1.0
 * Description: 网络请求的回调，对用户透明，展示
 */
public interface INetCallback<T> {
    /**
     * 成功的回调
     * @param result
     */
    void onSuccess(T result);

    /**
     * 失败的回调
     * @param e
     */
    void onFail(Exception e);

}
