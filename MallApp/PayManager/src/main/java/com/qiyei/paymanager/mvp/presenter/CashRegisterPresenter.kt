package com.qiyei.paymanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.paymanager.mvp.view.ICashRegisterView
import com.qiyei.paymanager.service.IPayManagerService
import com.qiyei.sdk.server.base.BaseService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class CashRegisterPresenter @Inject constructor():BasePresenter<ICashRegisterView>() {

    @Inject
    lateinit var mPayManagerService: IPayManagerService

    /**
     *获取支付宝支付签名
     */
    fun getPaySign(orderId: Int, totalPrice: Long){
        mPayManagerService.getPaySign(orderId,totalPrice).execute(object :BaseObserver<String>(mView){
            override fun onNext(item: String) {
                super.onNext(item)
                mView.onPaySignCallback(item)
            }
        },mLifecycleProvider)
    }

    /**
     *刷新订单状态，已支付
     */
    fun payOrder(orderId: Int){
        mPayManagerService.payOrder(orderId).execute(object :BaseObserver<Boolean>(mView){
            override fun onNext(item: Boolean) {
                super.onNext(item)
                mView.onPayOrderCallback(item)
            }
        },mLifecycleProvider)
    }

}