package com.qiyei.sdk.http.okhttp;

import com.qiyei.sdk.log.LogUtil;

import com.qiyei.sdk.http.base.HttpHelper;
import com.qiyei.sdk.http.base.IHttpCallback;
import com.qiyei.sdk.http.base.IHttpEngine;
import com.qiyei.sdk.http.base.HttpRequest;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/29.
 * Version: 1.0
 * Description:
 */
public class OkHttpEngine implements IHttpEngine{

    /**
     * OkHttpClient客户端
     */
    private static OkHttpClient mClient = new OkHttpClient();


    @Override
    public void get(HttpRequest req, final IHttpCallback callback) {

        String url = HttpHelper.buildGetRequest(req);
        LogUtil.e("Get请求路径：", url);
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url);
        Request request = builder.build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()){
                    callback.onSuccess(response.body().string());
                }else {
                    callback.onFail(new Exception("error"));
                }
            }
        });
    }

    @Override
    public void post(HttpRequest httpRequest, final IHttpCallback callback) {

        RequestBody requestBody = buildPostRequest(httpRequest);

        Request request = new Request.Builder()
                .url(httpRequest.getUrl())
                .post(requestBody)
                .build();

        LogUtil.e("Post请求路径：", httpRequest.getUrl());

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFail(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()){
                    callback.onSuccess(response.body().string());
                }else {
                    callback.onFail(new Exception("error"));
                }
            }
        });

    }

    /**
     * 创建post请求的requestbody
     * @param request
     * @return
     */
    private RequestBody buildPostRequest(HttpRequest request){
        return null;
    }

}
