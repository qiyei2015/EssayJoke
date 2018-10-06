package com.qiyei.mall.goodsmanager.service

import com.qiyei.mall.goodsmanager.data.protocol.CartGoods
import io.reactivex.Observable

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface ICartService {

    /**
     * 获取购物车列表
     */
    fun getCartList(): Observable<MutableList<CartGoods>?>

    /**
     * 添加商品到购物车
     */
    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
                goodsCount: Int, goodsSku: String): Observable<Int>

    /**
     * 删除购物车商品
     */
    fun deleteCartList(list: List<Int>): Observable<String>

    /**
     * 购物车结算
     */
    fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<Int>

}