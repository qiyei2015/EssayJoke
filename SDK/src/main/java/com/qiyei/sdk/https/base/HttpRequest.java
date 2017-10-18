package com.qiyei.sdk.https.base;

import java.util.Map;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/29.
 * Version: 1.0
 * Description:
 */
public class HttpRequest {
    /**
     * 请求的url
     */
    protected String mUrl;
    /**
     * 请求的参数
     */
    protected Map<String,Object> mParams;
    /**
     * Http请求方式，Get,post等
     */
    protected RequestMethod mRequestMethod;
    /**
     * 是否缓存
     */
    protected boolean isCache;

    public HttpRequest() {
    }

    public HttpRequest(String url) {
        mUrl = url;
    }

    public HttpRequest(String url, Map<String, Object> params) {
        mUrl = url;
        mParams = params;
    }

    public HttpRequest(String url, Map<String, Object> params, RequestMethod requestMethod) {
        mUrl = url;
        mParams = params;
        mRequestMethod = requestMethod;
    }

    /**
     * @return {@link #mUrl}
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * @param url the {@link #mUrl} to set
     */
    public void setUrl(String url) {
        mUrl = url;
    }

    /**
     * @return {@link #mParams}
     */
    public Map<String, Object> getParams() {
        return mParams;
    }

    /**
     * @param params the {@link #mParams} to set
     */
    public void setParams(Map<String, Object> params) {
        mParams = params;
    }

    /**
     * @return {@link #mRequestMethod}
     */
    public RequestMethod getRequestMethod() {
        return mRequestMethod;
    }

    /**
     * @param method the {@link #mRequestMethod} to set
     */
    public void setRequestMethod(RequestMethod method) {
        mRequestMethod = method;
    }

    /**
     * @return {@link #isCache}
     */
    public boolean isUseCache() {
        return isCache;
    }

    /**
     * @param cache the {@link #isCache} to set
     */
    public void setUseCache(boolean cache) {
        isCache = cache;
    }
}
