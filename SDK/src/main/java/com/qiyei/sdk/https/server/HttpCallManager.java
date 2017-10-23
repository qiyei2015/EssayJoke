package com.qiyei.sdk.https.server;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: https请求管理器
 */
public class HttpCallManager {

    /**
     * 保存OkhttpCall请求的map
     */
    private Map<String, Call> mOkHttpCallMap;

    /**
     * 保存RetrofitCall请求的map
     */
    private Map<String, retrofit2.Call> mRetrofitCallMap;

    /**
     * 无参构造方法
     */
    private HttpCallManager() {
        mOkHttpCallMap = new HashMap<>(16);
        mRetrofitCallMap = new HashMap<>(16);
    }

    /**
     * 单例的内部类
     */
    public static class SingleHolder{
        private final static HttpCallManager sManager = new HttpCallManager();
    }

    /**
     * 单例获取方法
     * @return
     */
    public static HttpCallManager getInstance(){
        return SingleHolder.sManager;
    }

    /**
     * 添加Call
     * @param id
     * @param call
     */
    public void addCall(String id,Call call){
        mOkHttpCallMap.put(id,call);
    }

    /**
     * 添加Call
     * @param id
     * @param call
     */
    public void addCall(String id, retrofit2.Call call){
        mRetrofitCallMap.put(id,call);
    }

    /**
     * 查询Call
     * @param id
     * @return
     */
    public Call queryCall(String id){
        return mOkHttpCallMap.get(id);
    }

    /**
     * 查询Call
     * @param id
     * @return
     */
    public retrofit2.Call queryCall(String id,String type){
        return mRetrofitCallMap.get(id);
    }

    /**
     * 移除Call
     * @param id
     */
    public void removeCall(String id){
        if (queryCall(id) != null){
            mOkHttpCallMap.remove(id);
            return;
        }
        mRetrofitCallMap.remove(id);
    }

}
