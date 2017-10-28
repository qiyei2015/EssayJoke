package com.qiyei.sdk.https.api.request;


import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: http get请求
 */
public class HttpGetRequest<T> extends HttpRequest {

    /**
     * http GET 请求参数,GET请求将参数添加到url上
     */
    private Map<String,String> mParams;

    public HttpGetRequest(T body){
        super(body);
        mParams = new HashMap<>();
    }

    /**
     * @return {@link #mParams}
     */
    public Map<String, String> getParams() {
        return mParams;
    }

    /**
     * @param params the {@link #mParams} to set
     */
    public void setParams(Map<String, String> params) {
        mParams = params;
    }

}
