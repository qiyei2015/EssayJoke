package com.qiyei.sdk.https.api.request;



/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: http 文件上传请求
 */
public class HttpUploadRequest<T> extends HttpRequest {


    /**
     * Http请求构造函数
     *
     * @param content
     */
    public HttpUploadRequest(T content) {
        super(content);
    }
}
