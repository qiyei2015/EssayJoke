package com.qiyei.sdk.https.server.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.qiyei.sdk.https.server.HttpCallManager;
import com.qiyei.sdk.https.server.HttpResponse;
import com.qiyei.sdk.https.server.HttpUtil;
import com.qiyei.sdk.https.server.IHttpCallback;
import com.qiyei.sdk.https.server.IHttpEngine;
import com.qiyei.sdk.https.server.HttpTask;
import com.qiyei.sdk.https.server.IHttpTransferCallback;
import com.qiyei.sdk.https.server.ProgressResponseBody;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

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
    public <T, R> void enqueueGetCall(final HttpTask<T> task, final IHttpCallback<R> callback) {

        Call call = OkHttpHelper.buildGetCall(mClient,task);
        if (call == null){
            return ;
        }

        //添加call
        HttpCallManager.getInstance().addCall(task.getTaskId(),call);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                //移除call
                HttpCallManager.getInstance().removeCall(task.getTaskId());

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                //移除call
                HttpCallManager.getInstance().removeCall(task.getTaskId());

                String result = null;
                if (response != null && response.isSuccessful()) {
                    result = response.body().string();
                }
                final R obj = (R) HttpUtil.parseJson(result, task.getListener().getClass(), true);
                final HttpResponse<R> responseObj = new HttpResponse<R>(obj);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (responseObj != null) {
                            callback.onSuccess(responseObj);
                        } else {
                            callback.onFailure(new Exception("response is null"));
                        }
                    }
                });
            }
        });
    }

    @Override
    public <T, R> void enqueuePostCall(final HttpTask<T> task, final IHttpCallback<R> callback) {

        Call call = OkHttpHelper.buildPostCall(mClient,task);
        if (call == null){
            return ;
        }

        //添加call
        HttpCallManager.getInstance().addCall(task.getTaskId(),call);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                //移除call
                HttpCallManager.getInstance().removeCall(task.getTaskId());

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //移除call
                HttpCallManager.getInstance().removeCall(task.getTaskId());

                String result = null;
                if (response != null && response.isSuccessful()) {
                    result = response.body().string();
                }
                final R obj = (R) HttpUtil.parseJson(result, task.getListener().getClass(), true);
                final HttpResponse<R> responseObj = new HttpResponse<R>(obj);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (responseObj != null) {
                            callback.onSuccess(responseObj);
                        } else {
                            callback.onFailure(new Exception("response is null"));
                        }
                    }
                });
            }
        });
    }

    @Override
    public <T, R> void enqueueDownloadCall(final HttpTask<T> task, final IHttpTransferCallback<R> callback) {

        Call call = OkHttpHelper.buildDownloadCall(mClient,task);
        if (call == null){
            return ;
        }

        //添加call
        HttpCallManager.getInstance().addCall(task.getTaskId(),call);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                //移除call
                HttpCallManager.getInstance().removeCall(task.getTaskId());

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                ResponseBody responseBody = new ProgressResponseBody(response.body(), callback);
                //read the body to file
                BufferedSource source = responseBody.source();
                File outFile = new File(task.getRequest().getFilePath());
                outFile.delete();
                outFile.getParentFile().mkdirs();
                outFile.createNewFile();
                BufferedSink sink = Okio.buffer(Okio.sink(outFile));
                source.readAll(sink);
                sink.flush();
                source.close();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String success = "success";
                        final HttpResponse responseObj = new HttpResponse(success);
                        callback.onSuccess(responseObj);
                    }
                });

                //移除call
                HttpCallManager.getInstance().removeCall(task.getTaskId());
            }
        });
    }

    @Override
    public <T, R> void enqueueUploadCall(final HttpTask<T> task, final IHttpTransferCallback<R> callback) {

        Call call = OkHttpHelper.buildUploadCall(mClient,task,callback);
        if (call == null){
            return ;
        }

        //添加call
        HttpCallManager.getInstance().addCall(task.getTaskId(),call);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                //移除call
                HttpCallManager.getInstance().removeCall(task.getTaskId());

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                ResponseBody responseBody = new ProgressResponseBody(response.body(),callback);
                //read the body to file
                BufferedSource source = responseBody.source();
                File outFile = new File(task.getRequest().getFilePath());
                outFile.delete();
                outFile.getParentFile().mkdirs();
                outFile.createNewFile();
                BufferedSink sink = Okio.buffer(Okio.sink(outFile));
                source.readAll(sink);
                sink.flush();
                source.close();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String success = "success";
                        final HttpResponse responseObj = new HttpResponse(success);
                        callback.onSuccess(responseObj);
                    }
                });

                //移除call
                HttpCallManager.getInstance().removeCall(task.getTaskId());
            }
        });
    }


    @Override
    public void cancelHttpCall(String taskId) {
        Object object = HttpCallManager.getInstance().queryCall(taskId);
        if (object == null){
            return;
        }

        if (object instanceof Call){
            Call call = (Call) object;
            if (call != null && !call.isCanceled()){
                call.cancel();
            }
        }
    }

    @Override
    public String toString() {
        return "OkHttpEngine";
    }
}
