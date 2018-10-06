package com.qiyei.mall.ordermanager.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qiyei.framework.ui.fragment.BaseMVPFragment


import com.qiyei.mall.ordermanager.R
import com.qiyei.mall.ordermanager.injection.component.DaggerOrderManagerComponent
import com.qiyei.mall.ordermanager.mvp.presenter.OrderManagerPresenter
import com.qiyei.mall.ordermanager.mvp.view.IOrderManagerView


/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class OrderFragment : BaseMVPFragment<OrderManagerPresenter>(),IOrderManagerView {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.getString()
    }

    /**
     * 依赖注入
     */
    override fun initComponentInject() {
        DaggerOrderManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun getTAG(): String {
        return OrderFragment::class.java.simpleName
    }
}
