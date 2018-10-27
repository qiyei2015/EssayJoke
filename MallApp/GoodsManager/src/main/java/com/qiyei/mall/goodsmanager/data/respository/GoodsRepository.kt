package com.qiyei.mall.goodsmanager.data.respository

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.framework.net.RetrofitFactory
import com.qiyei.mall.goodsmanager.data.api.ICategoryApi
import com.qiyei.mall.goodsmanager.data.api.IGoodsApi
import com.qiyei.mall.goodsmanager.data.bean.Category
import com.qiyei.mall.goodsmanager.data.bean.Goods
import com.qiyei.mall.goodsmanager.data.protocol.CategoryReq
import com.qiyei.mall.goodsmanager.data.protocol.GoodsDetailReq
import com.qiyei.mall.goodsmanager.data.protocol.GoodsListByKeywordReq
import com.qiyei.mall.goodsmanager.data.protocol.GoodsListReq
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class GoodsRepository @Inject constructor(){

    /**
     * 根据分类搜索商品
     */
    fun getGoodsList(categoryId: Int, pageNo: Int): Observable<BaseResp<MutableList<Goods>?>> {
        return RetrofitFactory.INSTANCE.create(IGoodsApi::class.java).getGoodsList(GoodsListReq(categoryId, pageNo))
    }

    /**
     * 根据关键字搜索商品
     */
    fun getGoodsListByKeyword(keyword: String, pageNo: Int): Observable<BaseResp<MutableList<Goods>?>> {
        return RetrofitFactory.INSTANCE.create(IGoodsApi::class.java).getGoodsListByKeyword(GoodsListByKeywordReq(keyword, pageNo))
    }

    /**
     * 商品详情
     */
    fun getGoodsDetail(goodsId: Int): Observable<BaseResp<Goods>> {
        return RetrofitFactory.INSTANCE.create(IGoodsApi::class.java).getGoodsDetail(GoodsDetailReq(goodsId))
    }
}