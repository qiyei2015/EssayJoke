package com.qiyei.framework.net

import com.qiyei.framework.constant.MallConstant
import com.qiyei.sdk.https.server.okhttp.OkHttpFactory
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Created by qiyei2015 on 2018/9/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: kotlin单例
 */
const val baseUrl = MallConstant.SERVER_ADDRESS

class RetrofitFactory private constructor(){

    //伴生对象
    companion object {
        val INSTANCE: RetrofitFactory by lazy {
            RetrofitFactory()
        }
    }

    //实例
    private val retrofit:Retrofit
    //拦截器
    private val interceptor:Interceptor

    init {
        //初始化拦截器
        interceptor = Interceptor { chain ->
            val request = chain.request()
                    .newBuilder()
                    .addHeader("Content_Type", "application/json")
                    .addHeader("charset", "UTF-8")
                    .build()
            chain.proceed(request)
        }
        //需要对url校验的
        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpFactory.createOkHttpClient(interceptor))
                .build()

    }

    /**
     * 创建接口
     */
    fun <T> create(service:Class<T>):T{
        return retrofit.create(service)
    }
}