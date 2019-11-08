package com.qiyei.paymanager.data.api

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.paymanager.data.protocol.PayOrderReq
import com.qiyei.paymanager.data.protocol.PaySignReq
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * @author Created by qiyei2015 on 2018/10/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IPayApi {

    /**
     *获取支付宝支付签名
     */
    @POST("pay/getPaySign")
    fun getPaySign(@Body req: PaySignReq): Observable<BaseResp<String>>

    /**
     *刷新订单状态，已支付
     */
    @POST("order/pay")
    fun payOrder(@Body req: PayOrderReq): Observable<BaseResp<String>>

}