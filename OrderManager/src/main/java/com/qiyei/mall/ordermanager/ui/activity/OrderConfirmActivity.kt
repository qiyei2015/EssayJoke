package com.qiyei.mall.ordermanager.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.ordermanager.R
import com.qiyei.mall.ordermanager.injection.component.DaggerOrderManagerComponent
import com.qiyei.mall.ordermanager.injection.module.OrderManagerModule
import com.qiyei.mall.ordermanager.mvp.presenter.OrderConfirmPresenter
import com.qiyei.router.path.RouteMall

@Route(path = RouteMall.OrderManager.order_confirm)
class OrderConfirmActivity : BaseMVPActivity<OrderConfirmPresenter>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)
        initView()
    }

    override fun initComponentInject() {
        DaggerOrderManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .orderManagerModule(OrderManagerModule())
                .build()
                .inject(this)
    }

    private fun initView(){

    }
}
