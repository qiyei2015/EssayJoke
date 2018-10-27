package com.qiyei.mall.goodsmanager.mvp.presenter


import com.alibaba.android.arouter.facade.annotation.Autowired
import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.goodsmanager.data.bean.Goods
import com.qiyei.mall.goodsmanager.mvp.view.IGoodsSkuView
import com.qiyei.mall.goodsmanager.service.ICartService
import com.qiyei.mall.goodsmanager.service.IGoodsService
import com.qiyei.provider.service.mall.IGoodsManagerService
import com.qiyei.provider.service.mall.MallServiceConstant

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
    @Inject
    lateinit var mCartService:ICartService

    @Autowired(name = MallServiceConstant.GOODS_MANAGER_SERVICE_PATH)
    lateinit var mGoodsManagerService: IGoodsManagerService

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

    /**
     * 添加商品到购物车
     */
    fun addCart(goods: Goods,goodsCount: Int, goodsSku: String){
        mCartService.addCart(goods.id,goods.goodsDesc,goods.goodsDefaultIcon,goods.goodsDefaultPrice
                ,goodsCount,goodsSku).execute(object : BaseObserver<Int>(mView){
            override fun onNext(item: Int) {
                //保存数据
                mGoodsManagerService.updateCartGoodsCount(item)
                mView.onAddCartResult(item)
            }
        },mLifecycleProvider)
    }
}