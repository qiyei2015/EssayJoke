package com.qiyei.sdk.https.server.okhttp;

import com.qiyei.sdk.https.HTTP;
import com.qiyei.sdk.https.server.HttpTask;
import com.qiyei.sdk.https.server.ProgressRequestBody;
import com.qiyei.sdk.https.server.ProgressResponseBody;
import com.qiyei.sdk.log.LogManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Created by qiyei2015 on 2018/4/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 进度拦截器，实现上传下载的进度拦截
 */
public class ProgressInterceptor implements Interceptor {


    private final static String TAG = ProgressInterceptor.class.getSimpleName();

    /**
     * 带进度的响应体,用于文件下载
     */
    private ProgressResponseBody mProgressResponseBody;
    /**
     * 带进度的请求体，用于文件上传
     */
    private ProgressRequestBody mProgressRequestBody;

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();

        //取出okHttp tag保存的数据
        HttpTask task = (HttpTask) original.tag();
        if (task == null){
            return chain.proceed(original);
        }

        //上传请求
        if (HTTP.UPLOAD.equals(task.getRequest().getMethod())){
            //设置带进度的响应实体
            if (mProgressRequestBody != null){
                LogManager.i(TAG,"mProgressRequestBody:" + mProgressRequestBody);
                mProgressRequestBody.setRequestBody(original.body());
            }
        }

        //处理响应
        Response response = chain.proceed(original);
        //设置带进度的响应实体
        if (mProgressResponseBody != null){
            LogManager.i(TAG,"mProgressResponseBody:" + mProgressResponseBody);
            mProgressResponseBody.setResponseBody(response.body());
            return response.newBuilder().body(mProgressResponseBody).build();
        }

        return chain.proceed(original);
    }

    /**
     * @return {@link #mProgressResponseBody}
     */
    public ProgressResponseBody getProgressResponseBody() {
        return mProgressResponseBody;
    }

    /**
     * @param progressResponseBody the {@link #mProgressResponseBody} to set
     */
    public void setProgressResponseBody(ProgressResponseBody progressResponseBody) {
        mProgressResponseBody = progressResponseBody;
    }

    /**
     * @return {@link #mProgressRequestBody}
     */
    public ProgressRequestBody getProgressRequestBody() {
        return mProgressRequestBody;
    }

    /**
     * @param progressRequestBody the {@link #mProgressRequestBody} to set
     */
    public void setProgressRequestBody(ProgressRequestBody progressRequestBody) {
        mProgressRequestBody = progressRequestBody;
    }

}
