package com.qiyei.mall.ordermanager.data.bean

/**
 * @author Created by qiyei2015 on 2018/10/17.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
data class OrderGoods(val id: Int,
                 var goodsId: Int,
                 val goodsDesc: String,
                 val goodsIcon: String,
                 val goodsPrice: Long,
                 val goodsCount: Int,
                 val goodsSku: String,
                 val orderId: Int) {
}