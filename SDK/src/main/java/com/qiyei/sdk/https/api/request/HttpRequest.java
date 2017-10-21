package com.qiyei.sdk.https.api.request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: https请求
 */
public class HttpRequest<T> {

    /**
     * 请求的url
     */
    protected String mUrl;
    /**
     * 请求的Header参数
     */
    protected Map<String,String> mHeaderMap;
    /**
     * 请求体，外部传入
     */
    protected T mContent;
    /**
     * 是否缓存
     */
    protected boolean isCache;

    /**
     * Http请求构造函数
     * @param content
     */
    public HttpRequest(T content){
        mHeaderMap = new HashMap<>();
        isCache = true;
        mUrl = "";
        mContent = content;
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
     * @return {@link #mContent}
     */
    public T getContent() {
        return mContent;
    }

    /**
     * @param content the {@link #mContent} to set
     */
    public void setContent(T content) {
        mContent = content;
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
}
