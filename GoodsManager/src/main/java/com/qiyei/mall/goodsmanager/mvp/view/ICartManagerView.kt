package com.qiyei.mall.ordermanager.mvp.view

import com.qiyei.framework.mvp.view.IBaseView
import com.qiyei.mall.goodsmanager.data.protocol.CartGoods

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface ICartManagerView:IBaseView {

    /**
     * 获取购物车列表
     */
    fun onCartList(goodsList: MutableList<CartGoods>?)
    /**
     *删除购物车
     */
    fun onDeleteCartList(result: Boolean)
    /**
     * 提交购物车
     */
    fun onSubmitCartList(id: Int)

}