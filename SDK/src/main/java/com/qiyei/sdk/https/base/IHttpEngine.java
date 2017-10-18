package com.qiyei.sdk.https.base;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/29.
 * Version: 1.0
 * Description: http的引擎规范
 */
public interface IHttpEngine {

    /**
     * get请求
     * @param httpRequest
     * @param callback
     */
    void get(HttpRequest httpRequest, IHttpCallback callback);

    /**
     * post请求
     * @param httpRequest
     * @param callback
     */
    void post(HttpRequest httpRequest, IHttpCallback callback);


    //文件下载

    //文件下载



}
