package com.qiyei.sdk.https.api;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface IHttpTransferListener extends IHttpListener{

    /**
     * 进度回调
     * @param currentSize
     * @param totalSize
     */
    void onProgress(long currentSize, long totalSize);

}
