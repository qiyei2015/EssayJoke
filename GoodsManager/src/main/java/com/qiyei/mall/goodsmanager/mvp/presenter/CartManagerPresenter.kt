package com.qiyei.mall.ordermanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.goodsmanager.data.protocol.CartGoods
import com.qiyei.mall.goodsmanager.service.ICartService
import com.qiyei.mall.ordermanager.mvp.view.ICartManagerView
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class CartManagerPresenter @Inject constructor():BasePresenter<ICartManagerView>() {

    @Inject
    lateinit var mCartService: ICartService

    override fun getTAG(): String {
        return this::class.java.simpleName
    }

    /**
     * 获取购物车列表
     */
    fun getCartList(){
        mCartService.getCartList().execute(object : BaseObserver<MutableList<CartGoods>?>(mView){
            override fun onNext(item: MutableList<CartGoods>?) {
                mView.onCartList(item)
            }
        },mLifecycleProvider)
    }

    /**
     * 删除购物车商品
     */
    fun deleteCartList(list: List<Int>){
        mCartService.deleteCartList(list).execute(object : BaseObserver<Boolean>(mView){
            override fun onNext(item: Boolean) {
                mView.onDeleteCartList(item)
            }
        },mLifecycleProvider)
    }

    /**
     * 购物车结算
     */
    fun submitCart(list: MutableList<CartGoods>, totalPrice: Long){
        mCartService.submitCart(list,totalPrice).execute(object : BaseObserver<Int>(mView){
            override fun onNext(item: Int) {
                mView.onSubmitCartList(item)
            }
        },mLifecycleProvider)
    }

}