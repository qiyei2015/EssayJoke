package com.qiyei.sdk.https.server;


import android.support.v4.app.FragmentManager;

import com.qiyei.sdk.https.api.request.HttpDownloadRequest;
import com.qiyei.sdk.https.api.request.HttpGetRequest;
import com.qiyei.sdk.https.api.request.HttpPostRequest;
import com.qiyei.sdk.https.api.request.HttpUploadRequest;

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
     * @param request
     * @param callback
     * @return 返回task id
     */
    String get(FragmentManager fragmentManager, HttpGetRequest request, IHttpCallback callback);

    /**
     * post请求
     * @param fragmentManager
     * @param request
     * @param callback
     * @return 返回task id
     */
    String post(FragmentManager fragmentManager, HttpPostRequest request, IHttpCallback callback);


    /**
     * download 下载请求
     * @param fragmentManager
     * @param request
     * @param callback
     * @return 返回task id
     */
    String download(FragmentManager fragmentManager, HttpDownloadRequest request, IHttpTransferCallback callback);

    /**
     * upload请求
     * @param fragmentManager
     * @param request
     * @param callback
     * @return 返回task id
     */
    String upload(FragmentManager fragmentManager, HttpUploadRequest request, IHttpTransferCallback callback);

    /**
     * 取消http请求
     * @param taskId
     */
    void cancelHttpCall(String taskId);

}
