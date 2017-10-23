package com.qiyei.sdk.https.server.retrofit;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author Created by qiyei2015 on 2017/10/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: Retrofit 封装call接口类
 */
public interface IRetrofitService<T,R> {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("path")
    Call<R> createGetCall();

    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("xx")
    Call<R> createPostCall(@Body T body);
}
