package com.qiyei.mall.ordermanager.mvp.presenter

import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.mall.ordermanager.mvp.view.ICartManagerView
import com.qiyei.sdk.log.LogManager
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class CartManagerPresenter @Inject constructor():BasePresenter<ICartManagerView>() {

    override fun getTAG(): String {
        return CartManagerPresenter::class.java.simpleName
    }

    fun getString(){
        LogManager.i(getTAG(),"hello world")
    }

}