package com.qiyei.appdemo.net;

import com.qiyei.appdemo.model.DiscoverListResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * @author Created by qiyei2015 on 2017/10/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: retrofit请求示例
 */
public interface RetrofitApiService {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("discovery/v3/?version_name=5.7.0&device_platform=android&iid=6152551759&manifest_version_code=570&device_id=30036118478&longitude=113.000366&latitude=28.171377&device_brand=Xiaomi&ac=wifi&update_version_code=5701&app_name=joke_essay&aid=7")
    Call<DiscoverListResult> getDiscoverList();

}
