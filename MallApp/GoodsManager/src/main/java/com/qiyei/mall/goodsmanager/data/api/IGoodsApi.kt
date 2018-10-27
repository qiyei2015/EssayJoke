package com.qiyei.mall.goodsmanager.data.api

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.mall.goodsmanager.data.bean.Goods
import com.qiyei.mall.goodsmanager.data.protocol.GoodsDetailReq
import com.qiyei.mall.goodsmanager.data.protocol.GoodsListByKeywordReq
import com.qiyei.mall.goodsmanager.data.protocol.GoodsListReq
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IGoodsApi {

    /**
     *
     */
    @POST("goods/getGoodsList")
    fun getGoodsList(@Body req: GoodsListReq): Observable<BaseResp<MutableList<Goods>?>>

    /**
     * 获取商品列表
     */
    @POST("goods/getGoodsListByKeyword")
    fun getGoodsListByKeyword(@Body req: GoodsListByKeywordReq): Observable<BaseResp<MutableList<Goods>?>>

    /**
     * 获取商品详情
     */
    @POST("goods/getGoodsDetail")
    fun getGoodsDetail(@Body req: GoodsDetailReq): Observable<BaseResp<Goods>>

}