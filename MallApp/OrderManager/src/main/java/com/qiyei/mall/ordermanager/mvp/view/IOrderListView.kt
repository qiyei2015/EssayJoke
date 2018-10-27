package com.qiyei.mall.ordermanager.mvp.view

import com.qiyei.framework.mvp.view.IBaseView
import com.qiyei.mall.ordermanager.data.bean.Order

/**
 * @author Created by qiyei2015 on 2018/10/20.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IOrderListView:IBaseView {

    /**
     * 订单列表回调
     */
    fun onOrderList(list:MutableList<Order>?)

    fun onSubmitOrder(result: Boolean)

    fun onConfirmOrder(result: Boolean)

    fun onCancelOrder(result: Boolean)
}