package com.qiyei.sdk.https.server;

import com.qiyei.sdk.https.HTTP;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.TimeUtil;

import java.io.IOException;
import java.util.Map;

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

        Request original = chain.request();
        //取出okHttp tag保存的数据
        HttpTask task = (HttpTask) original.tag();
        if (task == null){
            return chain.proceed(original);
        }
        //获取taskId
        String taskId = task.getTaskId();

        //新的request
        Request newRequest = handleDns(task,original).build();

        long requestTime = System.currentTimeMillis();

        switch (task.getRequest().getMethod()){
            case HTTP.GET:
                LogManager.i(HTTP.TAG, getRequestInfo(newRequest,taskId,System.currentTimeMillis()));
                break;

            case HTTP.POST:
                RequestBody rb = newRequest.body();
                //只有普通的post请求打印 body UPLOAD 请求不打印
                if (rb != null) {
                    okio.Buffer buffer = new okio.Buffer();
                    rb.writeTo(buffer);
                    LogManager.i(HTTP.TAG, getRequestInfo(newRequest,taskId,System.currentTimeMillis()) + "\nbody = " + buffer.readUtf8());
                    buffer.clear();
                } else {
                    LogManager.i(HTTP.TAG, getRequestInfo(newRequest,taskId,System.currentTimeMillis()) + "\nbody = null");
                }

                break;

            case HTTP.DOWNLOAD:
                //Download时GET请求
                if (newRequest.method().equals(HTTP.GET)){
                    LogManager.i(HTTP.TAG, getRequestInfo(newRequest,taskId,System.currentTimeMillis()));
                }
                break;

            case HTTP.UPLOAD:
                LogManager.i(HTTP.TAG, getRequestInfo(newRequest,taskId,System.currentTimeMillis()));

                //设置带进度的响应实体
                if (mProgressRequestBody != null){
                    LogManager.i(TAG,"mProgressRequestBody:" + mProgressRequestBody);
                    mProgressRequestBody.setRequestBody(newRequest.body());
                    newRequest = newRequest.newBuilder().post(mProgressRequestBody).build();
                }

                break;

            default:
                break;
        }

        // TODO: 2017/11/4 后续再优化一下，保存缓存的响应等
        //获取到响应
        okhttp3.Response response = chain.proceed(newRequest);

        //设置带进度的响应实体
        if (mProgressResponseBody != null){
            LogManager.i(TAG,"mProgressResponseBody:" + mProgressResponseBody);
            mProgressResponseBody.setResponseBody(response.body());
            return response.newBuilder().body(mProgressResponseBody).build();
        }

        //下载请求 直接返回 防止崩溃OOM
        if (task.getRequest().getMethod().equals(HTTP.DOWNLOAD)){
            return response;
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
     * 添加DNS和Header
     * @param task
     * @param request
     * @return
     */
    private Request.Builder handleDns(HttpTask task,Request request){

        Request.Builder requestBuilder = request.newBuilder();

        Map<String,String> headerMap = task.getRequest().getHeaderMap();
        for (String key : headerMap.keySet()) {
            requestBuilder.addHeader(key, headerMap.get(key));
        }
//        String ip = DnsManager.getInstance().findIpByHost(original.url().host());
//        if (!TextUtils.isEmpty(ip)) {
//            requestBuilder.addHeader("host", original.url().host());
//            requestBuilder.url(original.url().toString().replace(original.url().host(), ip));
//        }
        return requestBuilder;
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
