package com.qiyei.mall.goodsmanager.data.api

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.mall.goodsmanager.data.protocol.AddCartReq
import com.qiyei.mall.goodsmanager.data.protocol.CartGoods
import com.qiyei.mall.goodsmanager.data.protocol.DeleteCartReq
import com.qiyei.mall.goodsmanager.data.protocol.SubmitCartReq
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface ICartApi {

    /**
     * 获取购物车列表
     */
    @POST("cart/getList")
    fun getCartList(): Observable<BaseResp<MutableList<CartGoods>?>>

    /**
     * 添加商品到购物车
     */
    @POST("cart/add")
    fun addCart(@Body req: AddCartReq): Observable<BaseResp<Int>>

    /**
     * 删除购物车商品
     */
    @POST("cart/delete")
    fun deleteCartList(@Body req: DeleteCartReq): Observable<BaseResp<String>>

    /**
     * 提交购物车商品
     */
    @POST("cart/submit")
    fun submitCart(@Body req: SubmitCartReq): Observable<BaseResp<Int>>
}