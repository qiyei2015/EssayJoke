package com.qiyei.performance.network;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Created by qiyei2015 on 2019/11/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 无网络缓存
 */
public class NoNetworkInterceptor implements Interceptor {

    //okhttp设置缓存即可
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        //没有网络连接
//        if (Utilss.isNetworkConnected()){
//            builder.cacheControl(CacheControl.FORCE_CACHE);
//        }
        return chain.proceed(builder.build());
    }

}
