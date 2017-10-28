package com.qiyei.sdk.https.server.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;


import com.google.gson.Gson;
import com.qiyei.sdk.https.HTTP;
import com.qiyei.sdk.https.dialog.LoadingManager;
import com.qiyei.sdk.https.server.HttpCallManager;
import com.qiyei.sdk.https.server.HttpResponse;
import com.qiyei.sdk.https.server.HttpUtil;
import com.qiyei.sdk.https.server.IHttpCallback;
import com.qiyei.sdk.https.server.IHttpEngine;
import com.qiyei.sdk.https.server.HttpTask;


import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 单纯使用OkHttp引擎
 */
public class OkHttpEngine implements IHttpEngine{

    /**
     * OkHttpClient
     */
    private OkHttpClient mClient;
    /**
     * 线程派发时的Handler
     */
    private Handler mHandler;

    /**
     * 构造函数
     */
    public OkHttpEngine(){
        mClient = OkHttpFactory.createOkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public <T,R> String execute(final FragmentManager fragmentManager, final HttpTask<T> task, final IHttpCallback<R> callback) {

        Call call = null;

        if (task.getRequest().getMethod().equals(HTTP.GET)){
            String url = OkHttpHelper.buildGetRequest(task.getRequest());
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url).tag(task);
            call = mClient.newCall(builder.build());
        }else if (task.getRequest().getMethod().equals(HTTP.POST)){
            String url = OkHttpHelper.buildPostRequest(task.getRequest());
            final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            Gson gson = new Gson();
            String json = gson.toJson(task.getRequest().getBody());
            RequestBody body = RequestBody.create(JSON, json);
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url).post(body).tag(task);
            call = mClient.newCall(builder.build());
        }

        HttpCallManager.getInstance().addCall(task.getTaskId(),call);

        LoadingManager.showDialog(fragmentManager,task.getTaskId());

        if (call == null){
            return null;
        }

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LoadingManager.dismissDialog(fragmentManager,task.getTaskId());
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String result = null;
                if (response != null && response.isSuccessful()){
                    result = response.body().string();
                }
                final R obj = (R) HttpUtil.parseJson(result,task.getListener().getClass(),true);
                final HttpResponse<R> responseObj = new HttpResponse<R>(obj);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LoadingManager.dismissDialog(fragmentManager,task.getTaskId());
                        if (responseObj != null){
                            callback.onSuccess(responseObj);
                        }else {
                            callback.onFailure(new Exception("response is null"));
                        }
                    }
                });
            }
        });

        return task.getTaskId();
    }

    @Override
    public <T, R> String execute(android.app.FragmentManager fragmentManager, HttpTask<T> task, IHttpCallback<R> callback) {
        return null;
    }

    @Override
    public void cancelHttpCall(String taskId) {
        Call call = HttpCallManager.getInstance().queryCall(taskId);
        if (call != null && !call.isCanceled()){
            call.cancel();
        }
    }


}
