package com.qiyei.appdemo.net;

import com.qiyei.appdemo.model.DiscoverListResult;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

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

    /**
     * 下载文件1
     * @return
     */
    @Streaming
    @GET("sw-search-sp/software/16d5a98d3e034/QQ_8.9.5.22062_setup.exe")
    Call<ResponseBody> downloadFileWithFixedUrl();

    /**
     * 下载文件2
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    /**
     * 上传文件
     * @param description
     * @param file
     * @return
     */
    @Multipart
    @POST("upload")
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);
}
