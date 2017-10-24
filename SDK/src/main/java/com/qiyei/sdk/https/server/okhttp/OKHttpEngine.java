package com.qiyei.sdk.https.server.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;




import com.qiyei.sdk.https.base.Https;
import com.qiyei.sdk.https.server.HttpCallManager;
import com.qiyei.sdk.https.server.HttpResponse;
import com.qiyei.sdk.https.server.HttpUtil;
import com.qiyei.sdk.https.server.IHttpCallback;
import com.qiyei.sdk.https.server.IHttpEngine;
import com.qiyei.sdk.https.server.task.HttpGetTask;
import com.qiyei.sdk.log.LogManager;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
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

    private Handler mHandler;

    /**
     * 构造函数
     */
    public OkHttpEngine(){
        mClient = OkHttpFactory.createOkHttpClient();
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public <T,R> String get(final FragmentManager fragmentManager, final HttpGetTask<T> task, final IHttpCallback<R> callback) {
        String url = OkHttpHelper.buildGetRequest(task.getRequest());
        LogManager.i(Https.TAG,Https.GET + " url --> " + url);

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url);
        Call call = mClient.newCall(builder.build());
        final String taskId = Https.GET + "_" + UUID.randomUUID().toString();

        HttpCallManager.getInstance().addCall(taskId,call);

        OkHttpHelper.showDialog(fragmentManager,taskId);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpHelper.dismissDialog(fragmentManager,taskId);
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
                LogManager.i(Https.TAG,"OkHttp --> " + result);
                final R obj = (R) HttpUtil.parseJson(result,task.getListener().getClass(),true);
                final HttpResponse<R> responseObj = new HttpResponse<R>(obj);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpHelper.dismissDialog(fragmentManager,taskId);
                        if (responseObj != null){
                            callback.onSuccess(responseObj);
                        }else {
                            callback.onFailure(new Exception("response is null"));
                        }
                    }
                });
            }
        });

        return taskId;
    }

    @Override
    public void cancelHttpCall(String taskId) {
        Call call = HttpCallManager.getInstance().queryCall(taskId);
        if (call != null){
            call.cancel();
        }
    }


}
