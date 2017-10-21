package com.qiyei.sdk.https.server;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: https内部回调
 */
public interface IHttpTransferCallback extends IHttpCallback {

    /**
     * 进度回调
     * @param currentSize
     * @param totalSize
     */
    void onProgress(long currentSize, long totalSize);
}
