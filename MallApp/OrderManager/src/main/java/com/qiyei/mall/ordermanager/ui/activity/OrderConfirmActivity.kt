package com.qiyei.mall.ordermanager.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.ordermanager.R
import com.qiyei.mall.ordermanager.common.OrderConstant
import com.qiyei.mall.ordermanager.data.bean.Order
import com.qiyei.mall.ordermanager.injection.component.DaggerOrderManagerComponent
import com.qiyei.mall.ordermanager.injection.module.OrderManagerModule
import com.qiyei.mall.ordermanager.mvp.presenter.OrderConfirmPresenter
import com.qiyei.mall.ordermanager.mvp.view.IOrderConfirmView
import com.qiyei.mall.ordermanager.ui.adapter.OrderGoodsAdapter
import com.qiyei.provider.router.RouteMall
import com.qiyei.provider.router.RouterMallConstant
import com.qiyei.provider.service.mall.IGoodsManagerService
import com.qiyei.provider.service.mall.MallServiceConstant
import kotlinx.android.synthetic.main.activity_order_confirm.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
/**
 * @author Created by qiyei2015 on 2018/10/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Route(path = RouteMall.OrderManager.ORDER_CONFIRM)
class OrderConfirmActivity : BaseMVPActivity<OrderConfirmPresenter>(),IOrderConfirmView {

    @Autowired(name = RouterMallConstant.KEY_ORDER_ID)
    @JvmField
    var mOrderId:Int = 0
    /**
     * Adapter
     */
    private lateinit var mOrderGoodsAdapter:OrderGoodsAdapter
    /**
     * 当前订单
     */
    private lateinit var mOrder:Order

    /**
     * 商品管理服务
     */
    @Autowired(name = MallServiceConstant.GOODS_MANAGER_PATH)
    lateinit var mGoodsManagerService: IGoodsManagerService


    companion object {
        const val REQUEST_CODE = 0x10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)
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

    override fun onClick(view: View) {
        super.onClick(view)
        when(view.id){
            R.id.mSelectShipTextView -> {
                startActivityForResult<ShipAddressActivity>(REQUEST_CODE)
            }
            R.id.mSubmitOrderButton -> {
                submitOrder()
            }
            R.id.mShipLayout -> {
                startActivityForResult<ShipAddressActivity>(REQUEST_CODE)
            }
        }
    }

    override fun onOrder(order: Order) {
        mOrder = order
        mOrderGoodsAdapter.datas = order.orderGoodsList
    }

    override fun onSubmitOrder(result: Boolean) {
        if (result){
            toast("提交订单 $result")
            //更新购物车数量
            val submitCount = mOrder.orderGoodsList.size
            mGoodsManagerService.updateCartGoodsCount(mGoodsManagerService.getCartGoodsCount() - submitCount)

            //跳转到去支付界面
            ARouter.getInstance().build(RouteMall.PayManager.CASH_PAY)
                    .withInt(RouterMallConstant.KEY_ORDER_ID,mOrderId)
                    .withLong(RouterMallConstant.KEY_ORDER_PRICE,mOrder.totalPrice)
                    .navigation()
            return
        }
        toast("提交订单失败")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK ){
            return
        }
        if (requestCode == REQUEST_CODE) {
            mOrder.shipAddress = data?.getParcelableExtra(OrderConstant.ADDRESS_ID)
            updateShipAddressView()
        }
    }


    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.order_confirm))
                .build()

        mSelectShipTextView.setOnClickListener(this)
        mSubmitOrderButton.setOnClickListener(this)
        mShipLayout.setOnClickListener(this)

        mShipLayout.visibility = View.GONE
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mOrderGoodsRecyclerView.layoutManager = layoutManager
        mOrderGoodsAdapter = OrderGoodsAdapter(this, arrayListOf())
        mOrderGoodsRecyclerView.adapter = mOrderGoodsAdapter
    }

    private fun loadData(){
        mPresenter.getOrderById(mOrderId)
    }

    /**
     * 提交订单
     */
    private fun submitOrder(){
        mPresenter.submitOrder(mOrder)
    }

    private fun updateShipAddressView(){
        mOrder.let {
            if (it.shipAddress == null){
                mShipLayout.visibility = View.GONE
                mSelectShipTextView.visibility = View.VISIBLE
            } else {
                mShipLayout.visibility = View.VISIBLE
                mSelectShipTextView.visibility = View.GONE
                mShipNameTextView.text = it.shipAddress?.shipUserName + " " + it.shipAddress?.shipUserMobile
                mShipAddressTv.text = it.shipAddress?.shipAddress
            }
        }
    }
}
