package com.qiyei.sdk.https.api;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface IHttpTransferListener<T> extends IHttpListener<T>{

    /**
     * 成功的回调
     * @param response
     */
    @Override
    void onSuccess(T response);

    /**
     * 进度回调
     * @param currentLength
     * @param totalLength
     */
    void onProgress(long currentLength, long totalLength);

    /**
     * 失败的回调
     * @param exception
     */
    @Override
    void onFailure(Exception exception);

}
