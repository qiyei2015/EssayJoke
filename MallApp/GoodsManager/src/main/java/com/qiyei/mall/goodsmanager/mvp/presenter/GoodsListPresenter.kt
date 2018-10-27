package com.qiyei.mall.goodsmanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.goodsmanager.data.bean.Goods
import com.qiyei.mall.goodsmanager.mvp.view.IGoodsListView
import com.qiyei.mall.goodsmanager.service.IGoodsService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class GoodsListPresenter @Inject constructor():BasePresenter<IGoodsListView>() {
    @Inject
    lateinit var mGoodService:IGoodsService

    override fun getTAG(): String {
        return GoodsListPresenter::class.java.simpleName
    }


    /**
     * 根据分类搜索商品
     */
    fun getGoodsList(categoryId: Int, pageNo: Int){
        mGoodService.getGoodsList(categoryId,pageNo).execute(object:BaseObserver<MutableList<Goods>?>(mView){
            override fun onNext(item: MutableList<Goods>?) {
                mView.onGoodsListResult(item)
            }
        },mLifecycleProvider)
    }

}