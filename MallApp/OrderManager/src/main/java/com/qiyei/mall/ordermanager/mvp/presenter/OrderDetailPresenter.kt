package com.qiyei.mall.ordermanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.ordermanager.data.bean.Order
import com.qiyei.mall.ordermanager.mvp.view.IOrderDetailView
import com.qiyei.mall.ordermanager.service.IOrderManagerService
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/27.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class OrderDetailPresenter @Inject constructor():BasePresenter<IOrderDetailView>() {

    @Inject
    lateinit var mOrderService: IOrderManagerService

    fun getOrderDetail(orderId:Int){
        mOrderService.getOrderById(orderId).execute(object :BaseObserver<Order>(mView){
            override fun onNext(item: Order) {
                super.onNext(item)
                mView.onOrderCallback(item)
            }
        },mLifecycleProvider)
    }
}