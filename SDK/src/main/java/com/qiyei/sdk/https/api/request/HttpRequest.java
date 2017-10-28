package com.qiyei.sdk.https.api.request;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: https请求
 */
public class HttpRequest<T> {
    // TODO: 2017/10/25  后续评估采用Builder模式来创建Request
    /**
     * 基础url，为了应对微服务有多个基础url
     */
    protected String mBaseUrl;
    /**
     * 请求的路径url，完整 mBaseUrl + mPathUrl
     */
    protected String mPathUrl;
    /**
     * 请求的Header参数
     */
    protected Map<String,String> mHeaderMap;
    /**
     * 请求体，外部传入
     */
    protected T mBody;
    /**
     * 是否缓存
     */
    protected boolean isCache;

    /**
     * retrofit等使用的接口类
     */
    protected Class<?> mApiClazz;

    /**
     * Http请求构造函数
     * @param body
     */
    public HttpRequest(T body){
        mHeaderMap = new HashMap<>();
        isCache = true;
        mPathUrl = "";
        mBody = body;
    }

    /**
     * @return {@link #mBaseUrl}
     */
    public String getBaseUrl() {
        return mBaseUrl;
    }

    /**
     * @param baseUrl the {@link #mBaseUrl} to set
     */
    public void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
    }
    /**
     * @return {@link #mPathUrl}
     */
    public String getPathUrl() {
        return mPathUrl;
    }

    /**
     * @param pathUrl the {@link #mPathUrl} to set
     */
    public void setPathUrl(String pathUrl) {
        mPathUrl = pathUrl;
    }

    /**
     * @return {@link #mHeaderMap}
     */
    public Map<String, String> getHeaderMap() {
        return mHeaderMap;
    }

    /**
     * @param headerMap the {@link #mHeaderMap} to set
     */
    public void setHeaderMap(Map<String, String> headerMap) {
        mHeaderMap = headerMap;
    }

    /**
     * @return {@link #mBody}
     */
    public T getBody() {
        return mBody;
    }

    /**
     * @param body the {@link #mBody} to set
     */
    public void setBody(T body) {
        mBody = body;
    }

    /**
     * @return {@link #isCache}
     */
    public boolean isCache() {
        return isCache;
    }

    /**
     * @param cache the {@link #isCache} to set
     */
    public void setCache(boolean cache) {
        isCache = cache;
    }

    /**
     * @return {@link #mApiClazz}
     */
    public Class<?> getApiClazz() {
        return mApiClazz;
    }

    /**
     * @param apiClazz the {@link #mApiClazz} to set
     */
    public void setApiClazz(Class<?> apiClazz) {
        mApiClazz = apiClazz;
    }
}
