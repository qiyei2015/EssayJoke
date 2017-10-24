package com.qiyei.sdk.https.server.task;
import com.qiyei.sdk.https.api.listener.IHttpListener;

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
     * httptask tag
     */
    protected String mTag;

    /**
     * 请求回调
     */
    protected IHttpListener mListener;

    /**
     * 构造器
     * @param listener
     */
    public HttpTask(IHttpListener listener) {
        mListener = listener;
    }

    /**
     * 生成taskId
     */
    protected void init(){
        mTaskId = mTag + "_" + UUID.randomUUID().toString();
    }

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
