package com.qiyei.sdk.https.server;

import java.util.Map;

/**
 * @author Created by qiyei2015 on 2017/10/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class HttpResponse<R> {
    /**
     * 头部信息
     */
    private Map<String,String> mHeaderMap;
    /**
     * 内容
     */
    private R mContent;

    /**
     * 无参构造方法
     */
    public HttpResponse() {
    }

    /**
     * 构造方法
     * @param content 响应主题
     */
    public HttpResponse(R content) {
        mContent = content;
    }

    /**
     * @return {@link #mContent}
     */
    public R getContent() {
        return mContent;
    }

    /**
     * @param content the {@link #mContent} to set
     */
    public void setContent(R content) {
        mContent = content;
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
     * 判断响应是否是ok
     * @param response
     * @param <R>
     * @return
     */
    public static <R> boolean isOK(HttpResponse<R> response){
        if (response != null && response.getContent() != null){
            return true;
        }
        return false;
    }

}
