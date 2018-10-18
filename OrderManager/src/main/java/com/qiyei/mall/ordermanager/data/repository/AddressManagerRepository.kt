package com.qiyei.mall.ordermanager.data.repository

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.framework.net.RetrofitFactory
import com.qiyei.mall.ordermanager.data.api.IOrderManagerApi
import com.qiyei.mall.ordermanager.data.bean.Order
import com.qiyei.mall.ordermanager.data.protocol.*
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/17.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class AddressManagerRepository @Inject constructor() {

    /**
     * 取消订单
     */
    fun cancelOrder(orderId: Int): Observable<BaseResp<String>> {
        return RetrofitFactory.INSTANCE.create(IOrderManagerApi::class.java).cancelOrder(CancelOrderReq(orderId))
    }

    /**
     *
     */
    fun confirmOrder(orderId: Int): Observable<BaseResp<String>> {
        return RetrofitFactory.INSTANCE.create(IOrderManagerApi::class.java).confirmOrder(ConfirmOrderReq(orderId))
    }

    /**
     *
     */
    fun getOrderById(orderId: Int): Observable<BaseResp<Order>> {
        return RetrofitFactory.INSTANCE.create(IOrderManagerApi::class.java).getOrderById(GetOrderByIdReq(orderId))
    }

    /**
     * 根据状态查询订单列表
     */
    fun getOrderList(orderStatus: Int): Observable<BaseResp<MutableList<Order>?>> {
        return RetrofitFactory.INSTANCE.create(IOrderManagerApi::class.java).getOrderList(GetOrderListReq(orderStatus))
    }

    /**
     *提交订单
     */
    fun submitOrder(order: Order): Observable<BaseResp<String>> {
        return RetrofitFactory.INSTANCE.create(IOrderManagerApi::class.java).submitOrder(SubmitOrderReq(order))
    }
}