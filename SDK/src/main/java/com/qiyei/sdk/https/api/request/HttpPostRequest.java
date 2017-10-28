package com.qiyei.sdk.https.api.request;



/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: http post请求
 */
public class HttpPostRequest<T> extends HttpRequest {


    public HttpPostRequest(T body) {
        super(body);
    }

}
