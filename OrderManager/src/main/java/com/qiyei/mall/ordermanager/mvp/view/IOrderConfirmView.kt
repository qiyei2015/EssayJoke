package com.qiyei.mall.ordermanager.mvp.view

import com.qiyei.framework.mvp.view.IBaseView
import com.qiyei.mall.ordermanager.data.bean.Order

/**
 * @author Created by qiyei2015 on 2018/10/17.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IOrderConfirmView:IBaseView {

    /**
     * 订单回调
     */
    fun onOrder(order: Order)

    /**
     * 提交订单回调
     */
    fun onSubmitOrder(result: Boolean)

}