package com.qiyei.sdk.https.server;

import com.qiyei.sdk.https.HTTP;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.TimeUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Created by qiyei2015 on 2017/11/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MyInterceptor implements Interceptor{

    private final static String TAG = MyInterceptor.class.getSimpleName();

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
        long id = System.currentTimeMillis();
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder();

//            for (String key : headers.keySet()) {
//                requestBuilder.addHeader(key, headers.get(key));
//            }
//            String ip = DnsManager.getInstance().findIpByHost(original.url().host());
//            if (!TextUtils.isEmpty(ip)) {
//                requestBuilder.addHeader("host", original.url().host());
//                requestBuilder.url(original.url().toString().replace(original.url().host(), ip));
//            }

        Request request = requestBuilder.build();
        //利用okHttp 的tag来保存数据
        HttpTask task = (HttpTask) request.tag();
        String taskId = task.getTaskId();
        long requestTime = System.currentTimeMillis();

        //下载 或者上传直接返回
//        if (task.getRequest().getMethod().equals(HTTP.DOWNLOAD) || task.getRequest().getMethod().equals(HTTP.UPLOAD)){
//            // TODO: 2017/10/29 只用显示url即可
////                LogManager.i(HTTP.TAG, getRequestInfo(request,taskId,System.currentTimeMillis()));
//            return chain.proceed(request);
//        }

        if (request.method().contains(HTTP.GET)) {
            LogManager.i(HTTP.TAG, getRequestInfo(request,taskId,System.currentTimeMillis()));
        } else {
            RequestBody rb = request.body();
            if (rb != null) {
                okio.Buffer buffer = new okio.Buffer();
                rb.writeTo(buffer);
                LogManager.i(HTTP.TAG, getRequestInfo(request,taskId,System.currentTimeMillis()) + "\nbody = " + buffer.readUtf8());
                buffer.clear();
            } else {
                LogManager.i(HTTP.TAG, getRequestInfo(request,taskId,System.currentTimeMillis()));
            }
        }

        okhttp3.Response response = chain.proceed(request);

        //设置带进度的响应实体
        if (mProgressResponseBody != null){
            //LogManager.i(TAG,"mProgressResponseBody:" + mProgressResponseBody);
            mProgressResponseBody.setResponseBody(response.body());
            return response.newBuilder().body(mProgressResponseBody).build();
        }

        //防止崩溃OOM
        if (task.getRequest().getMethod().equals(HTTP.DOWNLOAD)){
            return chain.proceed(request);
        }

        okhttp3.MediaType mediaType = response.body().contentType();
        String body = response.body().string();
        LogManager.i(HTTP.TAG, getResponseInfo(taskId,requestTime,System.currentTimeMillis()) + "\nbody: " +  body);
        return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, body)).build();
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

    /**
     * 请求信息整理
     * @param request
     * @param taskId
     * @param time
     * @return
     */
    private String getRequestInfo(Request request,String taskId,long time){
        String requestInfo = "Request ---> time: " + TimeUtil.formatTime(time,TimeUtil.FORMAT_1)
                + " id: " + taskId  + " url = " + request.url();

        return requestInfo;
    }

    /**
     * 响应信息格式
     * @param taskId
     * @param requestTime
     * @param time
     * @return
     */
    private String getResponseInfo(String taskId,long requestTime,long time){
        String responseInfo = "Response --> time: " + TimeUtil.formatTime(time,TimeUtil.FORMAT_1)
                + " id: "  + taskId + " " + (System.currentTimeMillis() - requestTime) + "ms";
        return responseInfo;
    }

}
