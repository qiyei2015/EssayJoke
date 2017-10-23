package com.qiyei.sdk.https.server.task;
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
}
