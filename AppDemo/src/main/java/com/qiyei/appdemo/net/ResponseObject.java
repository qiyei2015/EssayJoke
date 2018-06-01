package com.qiyei.appdemo.net;


/**
 * @author Created by qiyei2015 on 2017/10/28.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 数据响应的基类
 */
public class ResponseObject<T> {

    /**
     * 成功响应的返回码
     */
    public static final int OK = 1000;
    /**
     * 成功响应的返回信息
     */
    public static final String SUCCESS = "success";

    /**
     * 请求返回码
     */
    private int code;

    /**
     * 请求返回信息
     */
    private String message;

    /**
     * 请求响应
     */
    private T content;

    /**
     * 判断响应是否成功
     * @param resp
     * @return
     */
    public static boolean isOK(ResponseObject<?> resp){
        return resp != null && resp.getCode() == OK && resp.getMessage().equals(SUCCESS);
    }

    /**
     * @return {@link #code}
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the {@link #code} to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return {@link #message}
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the {@link #message} to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return {@link #content}
     */
    public T getContent() {
        return content;
    }

    /**
     * @param content the {@link #content} to set
     */
    public void setContent(T content) {
        this.content = content;
    }
}
