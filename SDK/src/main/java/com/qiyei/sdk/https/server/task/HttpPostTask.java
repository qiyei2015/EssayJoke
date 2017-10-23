package com.qiyei.sdk.https.server.task;

import com.qiyei.sdk.https.api.request.HttpPostRequest;
import com.qiyei.sdk.https.base.Https;

/**
 * @author Created by qiyei2015 on 2017/10/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class HttpPostTask<T> extends HttpTask {

    /**
     * Http post请求
     */
    private HttpPostRequest<T> mRequest;

    /**
     * 构造方法
     * @param request
     */
    public HttpPostTask(HttpPostRequest<T> request) {
        mTag = Https.POST;
        mRequest = request;
        init();
    }

    /**
     * @return {@link #mRequest}
     */
    public HttpPostRequest<T> getRequest() {
        return mRequest;
    }

}
