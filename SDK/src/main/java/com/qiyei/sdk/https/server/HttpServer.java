package com.qiyei.sdk.https.server;

import android.content.Context;


import androidx.fragment.app.FragmentManager;

import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.https.IHttpExecutor;
import com.qiyei.sdk.https.api.IHttpListener;
import com.qiyei.sdk.https.api.HttpRequest;
import com.qiyei.sdk.https.HTTP;
import com.qiyei.sdk.https.api.IHttpTransferListener;
import com.qiyei.sdk.https.dialog.LoadingManager;
import com.qiyei.sdk.https.server.okhttp.OkHttpEngine;
import com.qiyei.sdk.https.server.retrofit.RetrofitEngine;
import com.qiyei.sdk.log.LogManager;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: http服务，处理http请求
 */
public class HttpServer implements IHttpExecutor{

    /**
     * Https引擎，http请求执行者
     */
    private IHttpEngine mEngine;

    /**
     * Http请求管理者
     */
    private HttpCallManager mCallManager;

    /**
     * Http构造方法
     * @param context
     */
    private HttpServer(Context context,IHttpEngine engine) {
        mEngine = engine;
        onCreate();
    }

    /**
     * 静态内部类
     */
    private static class SingleHolder{
        private final static HttpServer sServer = new HttpServer(RuntimeEnv.appContext,new RetrofitEngine());
    }

    /**
     * 获取默认的HttpServer
     * @return
     */
    public static HttpServer getDefault(){
        return SingleHolder.sServer;
    }

    /**
     * 创建函数
     */
    public void onCreate(){
        LogManager.i(HTTP.TAG,"HttpServer created ! engine is " + mEngine.toString());
    }

    /**
     * 销毁函数
     */
    public void onDestory(){
        //做一些清理工作
    }

    /**
     * 设置引擎
     * @param engine
     */
    public void setEngine(IHttpEngine engine){
        mEngine = engine;
    }

    /**
     * 执行https请求
     * @param request 请求参数
     * @param listener 回调listener
     * @param <T> 请求泛型参数
     * @param <R> 响应泛型参数
     * @return 该任务的taskId
     */
    @Override
    public <T,R> String execute(HttpRequest<T> request, IHttpListener<R> listener) {
        return execute((FragmentManager) null,request,listener);
    }

    /**
     * 执行https请求
     * @param fragmentManager 显示对话框的fragment
     * @param request 请求参数
     * @param listener 回调listener
     * @param <T> 请求泛型参数
     * @param <R> 响应泛型参数
     * @return 该任务的taskId
     */
    @Override
    public <T,R> String execute(final FragmentManager fragmentManager, HttpRequest<T> request, final IHttpListener<R> listener) {
        HttpTask<T> task = new HttpTask<T>(request.getMethod(),request, listener,fragmentManager);
        executeHttpTask(task,listener);
        return task.getTaskId();
    }

    /**
     * 执行https请求
     * @param fragmentManager 显示对话框的fragment
     * @param request 请求参数
     * @param listener 回调listener
     * @param <T> 请求泛型参数
     * @param <R> 响应泛型参数
     * @return 该任务的taskId
     */
    @Override
    public <T,R> String execute(android.app.FragmentManager fragmentManager, HttpRequest<T> request, IHttpListener<R> listener) {
        HttpTask<T> task = new HttpTask<T>(request.getMethod(),request, listener,fragmentManager);
        executeHttpTask(task,listener);
        return task.getTaskId();
    }

    /**
     * 取消网络请求
     * @param taskId 需要取消的 taskId
     */
    @Override
    public void cancel(String taskId) {
        mEngine.cancelHttpCall(taskId);
    }

    /**
     * Task进行分流
     * @param task
     * @param listener
     * @param <T>
     * @param <R>
     */
    private <T,R> void executeHttpTask(HttpTask<T> task,IHttpListener<R> listener){
        //将请求进行分发
        switch (task.getRequest().getMethod()){
            case HTTP.GET:
                executeGet(task,listener);
                break;
            case HTTP.POST:
                executePost(task,listener);
                break;
            case HTTP.DOWNLOAD:
                executeDownload(task,(IHttpTransferListener)listener);
                break;
            case HTTP.UPLOAD:
                executeUpload(task,(IHttpTransferListener)listener);
                break;
            default:
        }
    }


    /**
     * 执行GET POST请求
     */
    private <T,R> void executeGet(final HttpTask<T> task,final IHttpListener<R> listener){

        //显示对话框
        LoadingManager.showDialog(task.getFragmentManager(),task.getTaskId());

        mEngine.enqueueGetCall(task, new IHttpCallback<R>() {
            @Override
            public void onSuccess(HttpResponse<R> response) {

                LoadingManager.dismissDialog(task.getFragmentManager(),task.getTaskId());

                if (HttpResponse.isOK(response)){
                    listener.onSuccess(response.getContent());
                }else {
                    listener.onFailure(new Exception("is not ok"));
                }
            }

            @Override
            public void onFailure(Exception exception) {
                LoadingManager.dismissDialog(task.getFragmentManager(),task.getTaskId());
                LogManager.i(HTTP.TAG,"exception:" + exception.toString());
                listener.onFailure(exception);
            }
        });
    }

    /**
     * 执行GET POST请求
     */
    private <T,R> void executePost(final HttpTask<T> task,final IHttpListener<R> listener){
        //显示对话框
        LoadingManager.showDialog(task.getFragmentManager(),task.getTaskId());
        mEngine.enqueuePostCall(task, new IHttpCallback<R>() {
            @Override
            public void onSuccess(HttpResponse<R> response) {
                LoadingManager.dismissDialog(task.getFragmentManager(),task.getTaskId());

                if (HttpResponse.isOK(response)){
                    listener.onSuccess(response.getContent());
                }else {
                    listener.onFailure(new Exception("is not ok"));
                }
            }

            @Override
            public void onFailure(Exception exception) {
                LoadingManager.dismissDialog(task.getFragmentManager(),task.getTaskId());
                LogManager.i(HTTP.TAG,"exception:" + exception.toString());
                listener.onFailure(exception);
            }
        });
    }

    /**
     *
     * @param task
     * @param listener
     * @param <R>
     */
    private <T,R> void executeDownload(final HttpTask<T> task, final IHttpTransferListener<R> listener){

        LoadingManager.showProgressDialog(task.getFragmentManager(),task.getTaskId());

        mEngine.enqueueDownloadCall(task, new IHttpTransferCallback<R>() {
            @Override
            public void onProgress(long currentLength, long totalLength) {
                final int progress = (int) ((currentLength * 1.0 / totalLength) * 100);

                mEngine.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        LoadingManager.setProgress(task.getFragmentManager(),task.getTaskId(),progress);
                        listener.onProgress(currentLength,totalLength);
                    }
                });
            }

            @Override
            public void onSuccess(HttpResponse<R> response) {
                LoadingManager.dismissProgressDialog(task.getFragmentManager(),task.getTaskId());
                listener.onSuccess(response.getContent());
            }

            @Override
            public void onFailure(Exception exception) {
                LoadingManager.dismissProgressDialog(task.getFragmentManager(),task.getTaskId());
                listener.onFailure(exception);
            }
        });
    }

    /**
     *
     * @param task
     * @param listener
     * @param <R>
     */
    private <T,R> void executeUpload(final HttpTask<T> task, final IHttpTransferListener<R> listener){

        LoadingManager.showProgressDialog(task.getFragmentManager(),task.getTaskId());

        mEngine.enqueueUploadCall(task, new IHttpTransferCallback<R>() {
            @Override
            public void onProgress(long currentLength, long totalLength) {
                int progress = (int) ((currentLength * 1.0 / totalLength) * 100);
                LoadingManager.setProgress(task.getFragmentManager(),task.getTaskId(),progress);
                listener.onProgress(currentLength,totalLength);
            }

            @Override
            public void onSuccess(HttpResponse<R> response) {
                LoadingManager.dismissProgressDialog(task.getFragmentManager(),task.getTaskId());
                listener.onSuccess(response.getContent());
            }

            @Override
            public void onFailure(Exception exception) {
                LoadingManager.dismissProgressDialog(task.getFragmentManager(),task.getTaskId());
                listener.onFailure(exception);
            }
        });
    }

}
