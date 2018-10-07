package com.qiyei.mall.goodsmanager.mvp.view

import com.qiyei.framework.mvp.view.IBaseView
import com.qiyei.mall.goodsmanager.data.bean.Goods

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IGoodsSkuView:IBaseView {

    /**
     * 商品sku回调
     */
    fun onGoodsSkuResult(goods: Goods)
}