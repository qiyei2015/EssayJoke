package com.qiyei.mall.ordermanager.service.impl

import com.qiyei.framework.extend.baseRespConvert
import com.qiyei.framework.extend.baseRespConvertBoolean
import com.qiyei.mall.ordermanager.data.bean.Order
import com.qiyei.mall.ordermanager.data.repository.OrderManagerRepository
import com.qiyei.mall.ordermanager.service.IOrderManagerService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/17.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class OrderManagerServiceImpl @Inject constructor():IOrderManagerService {


    @Inject
    lateinit var mOrderManagerRepository: OrderManagerRepository

    override fun getOrderById(orderId: Int): Observable<Order> {
        return mOrderManagerRepository.getOrderById(orderId).baseRespConvert()
    }

    override fun submitOrder(order: Order): Observable<Boolean> {
        return mOrderManagerRepository.submitOrder(order).baseRespConvertBoolean()
    }

    override fun getOrderList(orderStatus: Int): Observable<MutableList<Order>?> {
        return mOrderManagerRepository.getOrderList(orderStatus).baseRespConvert()
    }

    override fun cancelOrder(orderId: Int): Observable<Boolean> {
        return mOrderManagerRepository.cancelOrder(orderId).baseRespConvertBoolean()
    }

    override fun confirmOrder(orderId: Int): Observable<Boolean> {
        return mOrderManagerRepository.confirmOrder(orderId).baseRespConvertBoolean()
    }
}