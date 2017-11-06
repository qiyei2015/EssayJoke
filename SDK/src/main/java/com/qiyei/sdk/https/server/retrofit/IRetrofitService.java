package com.qiyei.sdk.https.server.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * @author Created by qiyei2015 on 2017/11/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface IRetrofitService {

    /**
     * 获取发现列表
     * @param params get请求参数 建议都按照这样写，方便参数传递
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("discovery/")
    Call<Object> getDiscoverList(@QueryMap Map<String, String> params);
}
