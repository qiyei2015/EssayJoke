package com.qiyei.mall.ordermanager.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.ordermanager.R
import com.qiyei.mall.ordermanager.injection.component.DaggerOrderManagerComponent
import com.qiyei.mall.ordermanager.injection.module.OrderManagerModule
import com.qiyei.mall.ordermanager.mvp.presenter.OrderListPresenter
import com.qiyei.mall.ordermanager.mvp.view.IOrderListView
import com.qiyei.router.path.RouteMall

/**
 * @author Created by qiyei2015 on 2018/10/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Route(path = RouteMall.OrderManager.order_list)
class OrderListActivity : BaseMVPActivity<OrderListPresenter>(),IOrderListView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        initView()
    }

    override fun initComponentInject() {
        DaggerOrderManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .orderManagerModule(OrderManagerModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    private fun initView(){

    }

}
