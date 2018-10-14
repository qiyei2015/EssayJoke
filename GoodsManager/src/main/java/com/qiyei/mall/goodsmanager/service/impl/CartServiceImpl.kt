package com.qiyei.mall.goodsmanager.service.impl

import com.qiyei.framework.extend.baseRespConvert
import com.qiyei.framework.extend.baseRespConvertBoolean
import com.qiyei.mall.goodsmanager.data.protocol.CartGoods
import com.qiyei.mall.goodsmanager.data.respository.CartRepository
import com.qiyei.mall.goodsmanager.service.ICartService

import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class CartServiceImpl @Inject constructor():ICartService {

    @Inject
    lateinit var mRepository: CartRepository

    override fun getCartList(): Observable<MutableList<CartGoods>?> {
        return mRepository.getCartList().baseRespConvert()
    }

    override fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long, goodsCount: Int, goodsSku: String): Observable<Int> {
        return mRepository.addCart(goodsId,goodsDesc,goodsIcon,goodsPrice,goodsCount,goodsSku).baseRespConvert()
    }

    override fun deleteCartList(list: List<Int>): Observable<Boolean> {
        return mRepository.deleteCartList(list).baseRespConvertBoolean()
    }

    override fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<Int> {
        return mRepository.submitCart(list,totalPrice).baseRespConvert()
    }

}