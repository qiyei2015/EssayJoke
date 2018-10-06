package com.qiyei.mall.goodsmanager.data.respository

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.framework.net.RetrofitFactory
import com.qiyei.mall.goodsmanager.data.api.ICartApi
import com.qiyei.mall.goodsmanager.data.protocol.*
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class CartRepository @Inject constructor(){


    /**
     * 获取购物车列表
     */
    fun getCartList(): Observable<BaseResp<MutableList<CartGoods>?>> {
        return RetrofitFactory.INSTANCE.create(ICartApi::class.java).getCartList()
    }

    /**
     * 添加商品到购物车
     */
    fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long,
                goodsCount: Int, goodsSku: String): Observable<BaseResp<Int>> {
        return RetrofitFactory.INSTANCE.create(ICartApi::class.java)
                .addCart(AddCartReq(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku))
    }

    /**
     * 删除购物车商品
     */
    fun deleteCartList(list: List<Int>): Observable<BaseResp<String>> {
        return RetrofitFactory.INSTANCE.create(ICartApi::class.java).deleteCartList(DeleteCartReq(list))
    }

    /**
     * 购物车结算
     */
    fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<BaseResp<Int>> {
        return RetrofitFactory.INSTANCE.create(ICartApi::class.java).submitCart(SubmitCartReq(list, totalPrice))
    }
}