package com.qiyei.sdk.https.api;

public class Response<T> {

    /**
     * 业务状态码
     */
    private int code;
    /**
     * 业务响应信息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

    /**
     * @author Created by qiyei2015 on 2020/2/15.
     * @version: 1.0
     * @email: 1273482124@qq.com
     * @description: 自定义响应数据结构
     * 				本类可提供给 H5/ios/安卓/公众号/小程序 使用
     * 				前端接受此类数据（json object)后，可自行根据业务去实现相关功能
     *
     * 				200：表示成功
     * 				500：表示错误，错误信息在msg字段中
     * 				501：bean验证错误，不管多少个错误都以map形式返回
     * 				502：拦截器拦截到用户token出错
     * 				555：异常抛出信息
     * 				556: 用户qq校验异常
     */
    public static enum Code {

        SUCCESS(200,"SUCCESS"),
        ERROR(500,"ERROR"),
        NEED_LOGIN(501,"NEED_LOGIN"),
        ILLEGAL_ARGUMENT(502,"ILLEGAL_ARGUMENT");

        private final int code;
        private final String desc;


        Code(int code, String desc){
            this.code = code;
            this.desc = desc;
        }

        public int getCode(){
            return code;
        }
        public String getDesc(){
            return desc;
        }

    }

    public boolean isSuccess(){
        return this.code == Code.SUCCESS.getCode();
    }

    public int getCode(){
        return code;
    }

    public T getData(){
        return data;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
