package com.qiyei.paymanager.service

import io.reactivex.Observable

/**
 * @author Created by qiyei2015 on 2018/10/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IPayManagerService {

    /**
     *获取支付宝支付签名
     */
    fun getPaySign(orderId: Int, totalPrice: Long): Observable<String>

    /**
     *刷新订单状态，已支付
     */
    fun payOrder(orderId: Int): Observable<Boolean>
}