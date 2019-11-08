package com.qiyei.provider.service.mall

import com.qiyei.provider.service.IBaseProvider

/**
 * @author Created by qiyei2015 on 2018/10/26.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:商品管理模块对外暴露的Service
 */
interface IGoodsManagerService :IBaseProvider{

    /**
     * 更新购物车中
     */
    fun updateCartGoodsCount(count:Int)

    /**
     * 获取购物车商品数量
     */
    fun getCartGoodsCount():Int


}