package com.qiyei.mall.ordermanager.service

import com.qiyei.mall.ordermanager.data.bean.Order
import io.reactivex.Observable

/**
 * @author Created by qiyei2015 on 2018/10/17.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IOrderManagerService {

    /**
     * 根据ID查询订单
     */
    fun getOrderById(orderId: Int): Observable<Order>

    /**
     *提交订单
     */
    fun submitOrder(order: Order): Observable<Boolean>

    /**
     * 根据状态查询订单列表
     */
    fun getOrderList(orderStatus: Int): Observable<MutableList<Order>?>

    /**
     * 取消订单
     */
    fun cancelOrder(orderId: Int): Observable<Boolean>

    /**
     * 确认订单
     */
    fun confirmOrder(orderId: Int): Observable<Boolean>
}