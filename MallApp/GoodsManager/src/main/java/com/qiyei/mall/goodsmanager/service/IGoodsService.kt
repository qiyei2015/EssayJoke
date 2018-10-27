package com.qiyei.mall.goodsmanager.service


import com.qiyei.mall.goodsmanager.data.bean.Goods
import io.reactivex.Observable

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IGoodsService {

    /**
     * 根据分类搜索商品
     */
    fun getGoodsList(categoryId: Int, pageNo: Int): Observable<MutableList<Goods>?>

    /**
     * 根据关键字搜索商品
     */
    fun getGoodsListByKeyword(keyword: String, pageNo: Int): Observable<MutableList<Goods>?>

    /**
     * 商品详情
     */
    fun getGoodsDetail(goodsId: Int): Observable<Goods>

}