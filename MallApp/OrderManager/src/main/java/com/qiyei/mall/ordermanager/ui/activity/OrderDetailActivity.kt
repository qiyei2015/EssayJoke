package com.qiyei.mall.ordermanager.ui.activity


import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.framework.util.YuanFenConverter
import com.qiyei.mall.ordermanager.R
import com.qiyei.mall.ordermanager.data.bean.Order
import com.qiyei.mall.ordermanager.injection.component.DaggerOrderManagerComponent
import com.qiyei.mall.ordermanager.injection.module.OrderManagerModule
import com.qiyei.mall.ordermanager.mvp.presenter.OrderDetailPresenter
import com.qiyei.mall.ordermanager.mvp.view.IOrderDetailView
import com.qiyei.mall.ordermanager.ui.adapter.OrderGoodsAdapter
import com.qiyei.provider.router.RouteMall
import com.qiyei.provider.router.RouterMallConstant
import kotlinx.android.synthetic.main.activity_order_detail.*

/**
 * @author Created by qiyei2015 on 2018/10/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:订单详情页面
 */

@Route(path = RouteMall.OrderManager.ORDER_DETAIL)
class OrderDetailActivity : BaseMVPActivity<OrderDetailPresenter>(),IOrderDetailView {

    @Autowired(name = RouterMallConstant.KEY_ORDER_ID)
    @JvmField
    var mOrderId:Int = 0

    /**
     * 订单Adapter
     */
    private lateinit var mAdapter:OrderGoodsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        initView()
        loadData()
    }

    override fun initComponentInject() {
        DaggerOrderManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .orderManagerModule(OrderManagerModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onOrderCallback(order: Order) {
        mShipNameTv.setContentText(order.shipAddress!!.shipUserName)
        mShipMobileTv.setContentText(order.shipAddress!!.shipUserMobile)
        mShipAddressTv.setContentText(order.shipAddress!!.shipAddress)
        mTotalPriceTv.setContentText(YuanFenConverter.changeF2YWithUnit(order.totalPrice))
        mAdapter.datas = order.orderGoodsList
    }

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.order_detail))
                .build()
        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this, arrayListOf())
        mOrderGoodsRv.adapter = mAdapter
    }

    private fun loadData(){
        mPresenter.getOrderDetail(mOrderId)
    }

}
