package com.qiyei.mall.ordermanager.mvp.view

import com.qiyei.framework.mvp.view.IBaseView
import com.qiyei.mall.ordermanager.data.bean.Order

/**
 * @author Created by qiyei2015 on 2018/10/27.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IOrderDetailView:IBaseView {

    fun onOrderCallback(order:Order)
}