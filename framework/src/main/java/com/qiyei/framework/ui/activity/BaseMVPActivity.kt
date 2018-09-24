package com.qiyei.framework.ui.activity

import android.os.Bundle
import com.qiyei.framework.inject.DaggerActivityComponent
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.mvp.view.IBaseView
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: MVP基类Activity，持有Presenter
 */
open class BaseMVPActivity<T:BasePresenter<*>> :BaseActivity(),IBaseView{
    @Inject
    lateinit var mPresenter:T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerActivityComponent.create().inject(this)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(text: String) {
        toast(text)
    }

}