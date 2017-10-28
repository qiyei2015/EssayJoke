package com.qiyei.appdemo.net;

import com.qiyei.appdemo.model.DiscoverListResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author Created by qiyei2015 on 2017/10/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: retrofit请求示例
 */
public interface RetrofitApiService {

    // TODO: 2017/10/25 后期可以考虑其他的写法形式 
    /**
     * 获取发现列表
     * @param params get请求参数 建议都按照这样写，方便参数传递
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("discovery/v3/")
    Call<DiscoverListResult> getDiscoverList(@QueryMap Map<String, String> params);

    /**
     *  post请求测试
     * @param req post请求参数
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("user/register")
    Call<ResponseObject<Bean2>> getPostCall(@Body RequestObject<Bean2> req);

}
