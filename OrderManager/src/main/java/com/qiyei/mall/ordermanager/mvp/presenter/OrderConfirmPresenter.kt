package com.qiyei.mall.ordermanager.mvp.presenter

import com.qiyei.framework.mvp.presenter.BasePresenter
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


}