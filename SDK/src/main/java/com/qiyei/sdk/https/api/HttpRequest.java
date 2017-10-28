package com.qiyei.sdk.https.api;

import com.qiyei.sdk.https.HTTP;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: https请求
 */
public class HttpRequest<T> {
    // TODO: 2017/10/25  后续评估采用Builder模式来创建Request

    /**
     * GET请求
     */
    public static final String GET = HTTP.GET;

    /**
     * POST 请求
     */
    public static final String POST = HTTP.POST;

    /**
     * DOWNLOAD 下载文件请求
     */
    public static final String DOWNLOAD = HTTP.DOWNLOAD;

    /**
     * UPLOAD 上传文件请求
     */
    public static final String UPLOAD = HTTP.UPLOAD;

    /**
     * Http请求方式
     */
    private String mMethod;
    /**
     * 基础url，为了应对微服务有多个基础url
     */
    private String mBaseUrl;
    /**
     * 请求的路径url，完整 mBaseUrl + mPathUrl
     */
    private String mPathUrl;
    /**
     * 请求的Header参数
     */
    private Map<String,String> mHeaderMap;
    /**
     * 请求体，外部传入
     */
    private T mBody;
    /**
     * 文件上传，文件下载的路径
     */
    private String mFilePath;
    /**
     * 是否缓存
     */
    private boolean isCache;

    /**
     * retrofit等使用的接口类
     */
    private Class<?> mApiClazz;

    /**
     * Http请求构造函数
     */
    private HttpRequest(){
        mHeaderMap = new HashMap<>();
        isCache = true;
        mBody = null;
    }


    /**
     * build设计模式
     */
    public static class Builder<T>{
        /**
         * HttpRequest引用
         */
        HttpRequest request;

        /**
         * Builder的构造方法
         */
        public Builder(){
            request = new HttpRequest();
            //默认为GET请求
            get();
        }

        /**
         * 设置GET请求
         * @return 当前对象
         */
        public Builder get(){
            request.mMethod = GET;
            return this;
        }

        /**
         * 设置POST请求
         * @return 当前对象
         */
        public Builder post(){
            request.mMethod = POST;
            return this;
        }

        /**
         * 设置DOWNLOAD请求
         * @return 当前对象
         */
        public Builder download(){
            request.mMethod = DOWNLOAD;
            return this;
        }

        /**
         * 设置UPLOAD请求
         * @return 当前对象
         */
        public Builder upload(){
            request.mMethod = UPLOAD;
            return this;
        }

        /**
         * @param baseUrl the {@link #mBaseUrl} to set
         * @return 当前对象
         */
        public Builder setBaseUrl(String baseUrl) {
            request.mBaseUrl = baseUrl;
            return this;
        }

        /**
         * @param pathUrl the {@link #mPathUrl} to set
         * @return 当前对象
         */
        public Builder setPathUrl(String pathUrl) {
            request.mPathUrl = pathUrl;
            return this;
        }

        /**
         * @param headerMap the {@link #mHeaderMap} to set
         * @return 当前对象
         */
        public Builder setHeaderMap(Map<String, String> headerMap) {
            request.mHeaderMap = headerMap;
            return this;
        }

        /**
         * @param body the {@link #mBody} to set
         * @return 当前对象
         */
        public Builder setBody(T body) {
            request.mBody = body;
            return this;
        }

        /**
         * @param filePath the {@link #mFilePath} to set
         * @return 当前对象
         */
        public Builder setFilePath(String filePath) {
            request.mFilePath = filePath;
            return this;
        }

        /**
         * @param cache the {@link #isCache} to set
         * @return 当前对象
         */
        public Builder setCache(boolean cache) {
            request.isCache = cache;
            return this;
        }

        /**
         * @param apiClazz the {@link #mApiClazz} to set
         * @return 当前对象
         */
        public Builder setApiClazz(Class<?> apiClazz) {
            request.mApiClazz = apiClazz;
            return this;
        }

        /**
         * 创建HttpRequest
         * @return
         */
        public HttpRequest build(){
            return request;
        }

    }

    /**
     * @return {@link #mMethod}
     */
    public String getMethod() {
        return mMethod;
    }

    /**
     * @return {@link #mBaseUrl}
     */
    public String getBaseUrl() {
        return mBaseUrl;
    }

    /**
     * @return {@link #mPathUrl}
     */
    public String getPathUrl() {
        return mPathUrl;
    }

    /**
     * @return {@link #mHeaderMap}
     */
    public Map<String, String> getHeaderMap() {
        return mHeaderMap;
    }

    /**
     * @return {@link #mBody}
     */
    public T getBody() {
        return mBody;
    }

    /**
     * @return {@link #mFilePath}
     */
    public String getFilePath() {
        return mFilePath;
    }

    /**
     * @return {@link #isCache}
     */
    public boolean isCache() {
        return isCache;
    }

    /**
     * @return {@link #mApiClazz}
     */
    public Class<?> getApiClazz() {
        return mApiClazz;
    }

}
