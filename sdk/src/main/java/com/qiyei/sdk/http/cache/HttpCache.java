package com.qiyei.sdk.http.cache;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/5.
 * Version: 1.0
 * Description: Http网络请求的缓存
 */
public class HttpCache {
    /**
     * 缓存的key,一般为url+params MD5加密
     */
    private String key;
    /**
     * 缓存的结果，一般为json字符串
     */
    private String value;

    /**
     * 数据库操作使用
     */
    public interface ${
        /**
         * key
         */
        String key = "key";
        /**
         * value
         */
        String value = "value";
    }


    public HttpCache() {
    }

    public HttpCache(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return {@link #key}
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the {@link #key} to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return {@link #value}
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the {@link #value} to set
     */
    public void setValue(String value) {
        this.value = value;
    }

}
