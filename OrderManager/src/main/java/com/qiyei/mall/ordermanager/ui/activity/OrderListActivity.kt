package com.qiyei.mall.ordermanager.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.activity.BaseActivity
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.ordermanager.R
import com.qiyei.mall.ordermanager.injection.component.DaggerOrderManagerComponent
import com.qiyei.mall.ordermanager.injection.module.OrderManagerModule
import com.qiyei.mall.ordermanager.mvp.presenter.OrderListPresenter
import com.qiyei.mall.ordermanager.mvp.view.IOrderListView
import com.qiyei.mall.ordermanager.ui.adapter.OrderListPagerAdapter
import com.qiyei.router.path.RouteMall
import kotlinx.android.synthetic.main.activity_order_list.*

/**
 * @author Created by qiyei2015 on 2018/10/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Route(path = RouteMall.OrderManager.order_list)
class OrderListActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        initView()
    }

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.my_orders))
                .build()
        mOrderListTabLayout.tabMode = TabLayout.MODE_FIXED
        mOrderListViewPager.adapter = OrderListPagerAdapter(supportFragmentManager)
        mOrderListTabLayout.setupWithViewPager(mOrderListViewPager)
    }

}
