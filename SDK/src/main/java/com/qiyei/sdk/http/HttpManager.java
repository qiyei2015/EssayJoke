package com.qiyei.sdk.http;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.LruCache;

import com.qiyei.sdk.http.base.IHttpCallback;
import com.qiyei.sdk.http.base.IHttpEngine;
import com.qiyei.sdk.http.base.HttpRequest;
import com.qiyei.sdk.http.base.INetCallback;
import com.qiyei.sdk.http.dialog.LoadingDialog;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.MD5Util;

import java.util.Map;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/29.
 * Version: 1.0
 * Description:
 */
public class HttpManager implements IHttpExecutor{

    private static final String TAG = "HTTP";

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
    @Override
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
    @Override
    public <T> String execute(FragmentManager fragmentManager,HttpRequest request, final INetCallback<T> callback){

        if (request == null){
            return null;
        }

        mFragmentManager = fragmentManager;
        tag = request.getUrl();

        //显示加载对话框
        showDialog();

        //是否缓存
        final boolean cache = request.isUseCache();
        String url = HttpUtil.jointParams(request.getUrl(),request.getParams());
        final String urlKey = MD5Util.string2MD5(url);

        mHttpCallback = new IHttpCallback() {
            @Override
            public void onSuccess(String result) {

                if (cache){
                    String cache = HttpUtil.getCache(urlKey);
                    if (cache != null && cache.equals(result)){
                        //数据一致，不用刷新界面
                        LogManager.d(TAG,"数据一致，不用刷新界面");
                        //关闭加载对话框
                        dismissDialog();
                        return;
                    }
                }

                final T obj = (T) HttpUtil.parseJson(result,callback.getClass());

                if (cache) {
                    // 2.3 缓存数据
                    long num = HttpUtil.setCache(urlKey,result);
                    LogManager.d(TAG,"num --> " + num);
                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //关闭加载对话框
                        dismissDialog();

                        if (cache){
                            LogManager.d(TAG,"数据不一致，刷新界面");
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
                        dismissDialog();
                        callback.onFail(e);
                    }
                });
            }
        };

        if (cache){
            String cacheResult = HttpUtil.getCache(urlKey);
            //有缓存
            if (!TextUtils.isEmpty(cacheResult)){
                T obj = (T) HttpUtil.parseJson(cacheResult,callback.getClass());

                //关闭加载对话框
                dismissDialog();

                callback.onSuccess(obj);

                LogManager.d(TAG,"有缓存，使用缓存刷新界面");

                return "222";
                // TODO: 2017/6/5 后续再改
            }
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
    @Override
    public void cancel(String taskId){

    }

    /**
     * 显示对话框
     */
    void showDialog(){
        if (mFragmentManager == null){
            return;
        }
        mDialog = new LoadingDialog();
        mDialog.setCancelable(false);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(mDialog, tag);
        fragmentTransaction.commitNowAllowingStateLoss();//立即执行
    }

    /**
     * 取消对话框显示
     */
    void dismissDialog(){
        if (mFragmentManager == null){
            return;
        }
        LoadingDialog dialog = (LoadingDialog) mFragmentManager.findFragmentByTag(tag);
        if(dialog != null){
            dialog.dismissAllowingStateLoss();
        }
    }

}
