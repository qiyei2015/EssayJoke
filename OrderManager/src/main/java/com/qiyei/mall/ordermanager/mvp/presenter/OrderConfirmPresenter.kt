package com.qiyei.mall.ordermanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.ordermanager.data.bean.Order
import com.qiyei.mall.ordermanager.mvp.view.IOrderConfirmView
import com.qiyei.mall.ordermanager.service.IOrderManagerService
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/17.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class OrderConfirmPresenter @Inject constructor():BasePresenter<IOrderConfirmView>() {

    @Inject
    lateinit var mOrderService:IOrderManagerService

    /**
     * 根据id获取订单
     */
    fun getOrderById(orderId: Int){
        mOrderService.getOrderById(orderId).execute(object :BaseObserver<Order>(mView){
            override fun onNext(item: Order) {
                mView.onOrder(item)
            }
        },mLifecycleProvider)

    }

    /**
     *提交订单
     */
    fun submitOrder(order: Order){
        mOrderService.submitOrder(order).execute(object :BaseObserver<Boolean>(mView){
            override fun onNext(item: Boolean) {
                mView.onSubmitOrder(item)
            }
        },mLifecycleProvider)
    }

}