package com.qiyei.sdk.https.server.task;
import com.qiyei.sdk.https.api.listener.IHttpListener;
import com.qiyei.sdk.https.api.request.HttpRequest;
import com.qiyei.sdk.util.UUIDUtil;

import java.util.UUID;

/**
 * @author Created by qiyei2015 on 2017/10/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public abstract class HttpTask {
    /**
     * 任务id
     */
    protected String mTaskId;
    /**
     * http task tag
     */
    protected String mTag;

    /**
     * 请求回调
     */
    protected IHttpListener mListener;

    /**
     * 构造器
     * @param tag
     * @param listener
     */
    public HttpTask(String tag,IHttpListener listener) {
        mTag = tag;
        mListener = listener;
        mTaskId = mTag + "_" + UUIDUtil.get32UUID();
    }

    /**
     * 获取请求，由子类实现
     * @return
     */
    public abstract HttpRequest getRequest();

    /**
     * @return {@link #mTaskId}
     */
    public String getTaskId() {
        return mTaskId;
    }

    /**
     * @return {@link #mListener}
     */
    public IHttpListener getListener() {
        return mListener;
    }

}
