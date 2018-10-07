package com.qiyei.mall.goodsmanager.mvp.presenter


import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.mall.goodsmanager.mvp.view.IGoodsDetailView
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class GoodsDetailPresenter @Inject constructor():BasePresenter<IGoodsDetailView>() {


    override fun getTAG(): String {
        return GoodsDetailPresenter::class.java.simpleName
    }

    fun getCategory(id:Int){

    }

}