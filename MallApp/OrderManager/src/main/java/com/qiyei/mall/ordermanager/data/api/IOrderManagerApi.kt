package com.qiyei.mall.ordermanager.data.api

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.mall.ordermanager.data.bean.Order
import com.qiyei.mall.ordermanager.data.protocol.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * @author Created by qiyei2015 on 2018/10/17.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:订单业务接口
 */
interface IOrderManagerApi {

    /**
     *取消订单
     */
    @POST("order/cancel")
    fun cancelOrder(@Body req: CancelOrderReq): Observable<BaseResp<String>>

    /**
     * 确认订单
     */
    @POST("order/confirm")
    fun confirmOrder(@Body req: ConfirmOrderReq): Observable<BaseResp<String>>

    /**
     *根据ID获取订单
     */
    @POST("order/getOrderById")
    fun getOrderById(@Body req: GetOrderByIdReq): Observable<BaseResp<Order>>

    /**
     * 根据订单状态查询查询订单列表
     */
    @POST("order/getOrderList")
    fun getOrderList(@Body req: GetOrderListReq): Observable<BaseResp<MutableList<Order>?>>

    /**
     * 提交订单
     */
    @POST("order/submitOrder")
    fun submitOrder(@Body req: SubmitOrderReq): Observable<BaseResp<String>>
}