package com.qiyei.sdk.https.server;


import android.support.v4.app.FragmentManager;

import com.qiyei.sdk.https.api.request.HttpDownloadRequest;
import com.qiyei.sdk.https.api.request.HttpGetRequest;
import com.qiyei.sdk.https.api.request.HttpPostRequest;
import com.qiyei.sdk.https.api.request.HttpUploadRequest;
import com.qiyei.sdk.https.server.task.HttpGetTask;

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
    <T,R> String get(final FragmentManager fragmentManager, HttpGetTask<T> task, IHttpCallback<R> callback);

    /**
     * 取消http请求
     * @param taskId
     */
    void cancelHttpCall(String taskId);

}
