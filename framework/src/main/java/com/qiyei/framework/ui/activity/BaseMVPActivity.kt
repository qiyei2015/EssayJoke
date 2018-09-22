package com.qiyei.framework.ui.activity

import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.mvp.view.IBaseView

/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: MVP基类Activity，持有Presenter
 */
open class BaseMVPActivity<T:BasePresenter<*>> :BaseActivity(),IBaseView{
    lateinit var mPresenter:T


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(text: String) {

    }

}