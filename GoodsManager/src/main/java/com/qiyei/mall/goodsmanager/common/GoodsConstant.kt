package com.qiyei.mall.goodsmanager.common

import com.qiyei.sdk.dc.impl.DC

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class GoodsConstant: DC.UserData() {

    companion object {
        //搜索历史 本地存储
        const val SP_SEARCH_HISTORY = "search_history"
        //搜索商品类型
        const val KEY_SEARCH_GOODS_TYPE = "search_goods_type"
        //按分类搜索
        const  val SEARCH_GOODS_TYPE_CATEGORY = 0
        //按关键字搜索
        const  val SEARCH_GOODS_TYPE_KEYWORD = 1
        //商品分类ID
        const  val KEY_CATEGORY_ID = "category_id"
        //商品关键字
        const val KEY_GOODS_KEYWORD = "goods_keyword"
        //商品ID
        const  val KEY_GOODS_ID = "goods_id"
        //sku分隔标识
        const val SKU_SEPARATOR = ","
        //购物车数量
        const val CART_GOODS_COUNT = "cart_goods_count"
    }
}