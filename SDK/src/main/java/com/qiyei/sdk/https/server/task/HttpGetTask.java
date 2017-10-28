package com.qiyei.sdk.https.server.task;

import android.text.TextUtils;

import com.qiyei.sdk.https.api.listener.IHttpListener;
import com.qiyei.sdk.https.api.request.HttpGetRequest;
import com.qiyei.sdk.https.base.Http;

/**
 * @author Created by qiyei2015 on 2017/10/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class HttpGetTask<T> extends HttpTask{
    /**
     * http get请求
     */
    private HttpGetRequest<T> mRequest;

    /**
     * 构造方法
     * @param request http请求
     */
    public HttpGetTask(HttpGetRequest<T> request, IHttpListener listener) {
        super(Http.GET,listener);
        mRequest = request;
    }

    /**
     * @return {@link #mRequest}
     */
    @Override
    public HttpGetRequest<T> getRequest() {
        return mRequest;
    }

}
