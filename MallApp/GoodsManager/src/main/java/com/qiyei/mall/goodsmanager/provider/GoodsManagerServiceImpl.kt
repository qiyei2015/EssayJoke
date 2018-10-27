package com.qiyei.mall.goodsmanager.provider

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.qiyei.mall.goodsmanager.common.GoodsConstant
import com.qiyei.provider.service.mall.IGoodsManagerService
import com.qiyei.provider.service.mall.MallServiceConstant
import com.qiyei.sdk.dc.DataManager

/**
 * @author Created by qiyei2015 on 2018/10/26.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Route(path = MallServiceConstant.GOODS_MANAGER_SERVICE_PATH, name = MallServiceConstant.GOODS_MANAGER_SERVICE_NAME)
class GoodsManagerServiceImpl : IGoodsManagerService{

    override fun init(context: Context?) {

    }

    override fun getTAG(): String {
        return this::class.java.simpleName
    }

    override fun getCartGoodsCount(): Int {
        //保存数据
        val cartUri = DataManager.getInstance().getUri(GoodsConstant.javaClass, GoodsConstant.CART_GOODS_COUNT)
        return DataManager.getInstance().getInt(cartUri,0)
    }

    override fun updateCartGoodsCount(count: Int) {
        //保存数据
        val cartUri = DataManager.getInstance().getUri(GoodsConstant.javaClass, GoodsConstant.CART_GOODS_COUNT)
        DataManager.getInstance().setInt(cartUri,count)
    }

}