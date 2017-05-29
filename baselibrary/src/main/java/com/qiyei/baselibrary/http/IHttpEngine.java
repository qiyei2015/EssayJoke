package com.qiyei.baselibrary.http;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/29.
 * Version: 1.0
 * Description: http的引擎规范
 */
public interface IHttpEngine {

    /**
     * get请求
     * @param request
     * @param callback
     */
    void get(Request request,INetCallback callback);

    /**
     * post请求
     * @param request
     * @param callback
     */
    void post(Request request,INetCallback callback);


    //文件下载

    //文件下载



}
