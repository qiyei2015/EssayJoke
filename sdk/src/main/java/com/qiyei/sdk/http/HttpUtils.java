package com.qiyei.sdk.http;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.google.gson.Gson;
import com.qiyei.sdk.http.base.IHttpCallback;
import com.qiyei.sdk.http.base.IHttpEngine;
import com.qiyei.sdk.http.base.HttpRequest;
import com.qiyei.sdk.http.base.INetCallback;
import com.qiyei.sdk.http.dialog.LoadingDialog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/29.
 * Version: 1.0
 * Description:
 */
public class HttpUtils{

    private static final String TAG = HttpUtils.class.getSimpleName();

    /**
     * http引擎
     */
    private static IHttpEngine mEngine;
    /**
     * 所有网络请求的任务栈
     */
    private Map<String,Object> mTaskMap;
    /**
     * 网络层的回调，不对外展现
     */
    private IHttpCallback mHttpCallback;

    private FragmentManager mFragmentManager;
    private DialogFragment mDialog;
    private String tag = "";


    private Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 初始化引擎
     * @param engine
     */
    public static void init(IHttpEngine engine){
        mEngine = engine;
    }

    /**
     * 设置Http引擎
     * @param engine
     */
    public static void setHttpEngine(IHttpEngine engine){
        mEngine = engine;
    }

    /**
     * 执行http请求，并返回响应的task id
     * @param request
     * @param callback
     * @return
     */
    public <T> String execute(HttpRequest request, final INetCallback<T> callback){
        return execute(null,request,callback);
    }

    /**
     * 执行http请求，并返回响应的task id
     * @param fragmentManager
     * @param request
     * @param callback
     * @return
     */
    public <T> String execute(FragmentManager fragmentManager,HttpRequest request, final INetCallback<T> callback){

        mFragmentManager = fragmentManager;
        tag = request.getUrl();

        mHttpCallback = new IHttpCallback() {
            @Override
            public void onSuccess(String s) {


                Gson gson = new Gson();

                //获取type类型数组的第0个
                Type genType = callback.getClass().getGenericInterfaces()[0];
                Log.d(TAG,"genType:" + genType.toString());

                //判断是不是参数化类型
                Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                Log.d(TAG,"params:" + params.toString());

                Class<T> clazz = (Class) params[0];

                final T obj = (T) gson.fromJson(s,clazz);

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //关闭加载对话框
                        if (mFragmentManager != null) {
                            dismissDialog();
                        }
                        callback.onSuccess(obj);

                    }
                });
            }

            @Override
            public void onFail(final Exception e) {

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //关闭加载对话框
                        if (mFragmentManager != null) {
                            dismissDialog();
                        }
                        callback.onFail(e);
                    }
                });
            }
        };

        //显示加载对话框
        if (mFragmentManager != null) {
            showDialog();
        }
        switch (request.getRequestMethod()){
            case GET:
                mEngine.get(request,mHttpCallback);
                break;
            case POST:
                mEngine.post(request,mHttpCallback);
                break;
            default:
                break;
        }
        return null;
    }


    /**
     * 取消任务
     * @param taskId
     */
    public void cancel(String taskId){

    }

    void showDialog(){
        mDialog = new LoadingDialog();
        mDialog.setCancelable(false);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(mDialog, tag);
        fragmentTransaction.commitAllowingStateLoss();
//        mDialog.show(mFragmentManager,tag);
    }

    void dismissDialog(){
        DialogFragment dialog = (DialogFragment) mFragmentManager.findFragmentByTag(tag);
        if(dialog!= null){
            dialog.dismissAllowingStateLoss();
        }
    }



}
