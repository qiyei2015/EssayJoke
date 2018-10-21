package com.qiyei.mall.ordermanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.ordermanager.data.bean.Order
import com.qiyei.mall.ordermanager.mvp.view.IOrderListView
import com.qiyei.mall.ordermanager.service.IOrderManagerService
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/20.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class OrderListPresenter @Inject constructor():BasePresenter<IOrderListView>() {

    @Inject
    lateinit var mOrderService: IOrderManagerService

    /**
     * 根据状态获取订单
     */
    fun getOrderList(orderStatus: Int) {
        mOrderService.getOrderList(orderStatus).execute(object :BaseObserver<MutableList<Order>?>(mView){
            override fun onNext(item: MutableList<Order>?) {
                super.onNext(item)
                mView.onOrderList(item)
            }
        },mLifecycleProvider)

    }

    /**
     *提交订单
     */
    fun submitOrder(order: Order){
        mOrderService.submitOrder(order).execute(object :BaseObserver<Boolean>(mView){
            override fun onNext(item: Boolean) {
                super.onNext(item)
                mView.onSubmitOrder(item)
            }
        },mLifecycleProvider)
    }

    /**
     * 确认订单
     */
    fun confirmOrder(orderId: Int){
        mOrderService.confirmOrder(orderId).execute(object :BaseObserver<Boolean>(mView){
            override fun onNext(item: Boolean) {
                super.onNext(item)
                mView.onConfirmOrder(item)
            }
        },mLifecycleProvider)
    }

    /**
     * 取消订单
     */
    fun cancelOrder(orderId: Int){
        mOrderService.cancelOrder(orderId).execute(object :BaseObserver<Boolean>(mView){
            override fun onNext(item: Boolean) {
                super.onNext(item)
                mView.onCancelOrder(item)
            }
        },mLifecycleProvider)
    }
}