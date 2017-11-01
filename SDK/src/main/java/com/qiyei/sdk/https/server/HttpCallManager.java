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
    private Map<String, Object> mCallMap;


    /**
     * 无参构造方法
     */
    private HttpCallManager() {
        mCallMap = new HashMap<>(16);
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
    public void addCall(String id,Object call){
        mCallMap.put(id,call);
    }

    /**
     * 查询Call
     * @param id
     * @return
     */
    public Object queryCall(String id){
        return mCallMap.get(id);
    }

    /**
     * 移除Call
     * @param id
     */
    public void removeCall(String id){
        if (queryCall(id) != null){
            mCallMap.remove(id);
        }
    }

}
