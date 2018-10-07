package com.qiyei.mall.goodsmanager.mvp.presenter


import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.goodsmanager.data.bean.Goods
import com.qiyei.mall.goodsmanager.mvp.view.IGoodsSkuView
import com.qiyei.mall.goodsmanager.service.IGoodsService

import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class GoodsSkuPresenter @Inject constructor():BasePresenter<IGoodsSkuView>() {

    @Inject
    lateinit var mGoodsService:IGoodsService

    override fun getTAG(): String {
        return GoodsSkuPresenter::class.java.simpleName
    }

    /**
     * 获取商品Sku
     */
    fun getGoodsSku(goodsId: Int){
        mGoodsService.getGoodsDetail(goodsId).execute(object :BaseObserver<Goods>(mView){
            override fun onNext(item: Goods) {
                mView.onGoodsSkuResult(item)
            }
        },mLifecycleProvider)
    }

}