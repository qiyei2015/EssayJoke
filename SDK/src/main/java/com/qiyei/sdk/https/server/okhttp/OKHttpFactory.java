package com.qiyei.sdk.https.server.okhttp;

import okhttp3.OkHttpClient;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 创建OkHttpClient的工厂类
 */
public class OkHttpFactory {


    public static OkHttpClient createOkHttpClient(){

        return new OkHttpClient();
    }

}
