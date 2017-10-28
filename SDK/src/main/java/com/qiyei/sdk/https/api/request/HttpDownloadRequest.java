package com.qiyei.sdk.https.api.request;



/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: http文件下载请求
 */
public class HttpDownloadRequest<T> extends HttpRequest {


    /**
     * Http请求构造函数
     *
     * @param body
     */
    public HttpDownloadRequest(T body) {
        super(body);
    }
}
