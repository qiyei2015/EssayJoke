package com.qiyei.sdk.https.server.retrofit;

import com.qiyei.sdk.https.server.okhttp.OkHttpFactory;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Created by qiyei2015 on 2017/10/21.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 创建Retrofit对象的工厂类
 */
public class RetrofitFactory {

    /**
     * RetrofitMap
     */
    private static Map<String,Retrofit> sRetrofitMap = new HashMap<>();

    /**
     * 创建Retrofit实例
     * @param baseUrl 基础url
     * @return
     */
    public static Retrofit createRetrofit(String baseUrl){
        Retrofit retrofit = sRetrofitMap.get(baseUrl);
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .client(OkHttpFactory.createOkHttpClient())
                    .build();
        }
        return retrofit;
    }

    /**
     * 创建Retrofit实例
     * @param baseUrl 基础url
     * @param supportRx 是否支持Rxjava
     * @return
     */
    public static Retrofit createRetrofit(String baseUrl,boolean supportRx){
        Retrofit retrofit = sRetrofitMap.get(baseUrl);
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .client(OkHttpFactory.createOkHttpClient())
                    .build();
        }
        return retrofit;
    }
}
