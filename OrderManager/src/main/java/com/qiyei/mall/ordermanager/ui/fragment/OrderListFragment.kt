package com.qiyei.mall.ordermanager.ui.fragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.kennyc.view.MultiStateView
import com.qiyei.framework.constant.OrderConstant
import com.qiyei.framework.ui.fragment.BaseMVPFragment


import com.qiyei.mall.ordermanager.R
import com.qiyei.mall.ordermanager.data.bean.Order
import com.qiyei.mall.ordermanager.injection.component.DaggerOrderManagerComponent
import com.qiyei.mall.ordermanager.injection.module.OrderManagerModule
import com.qiyei.mall.ordermanager.mvp.presenter.OrderListPresenter
import com.qiyei.mall.ordermanager.mvp.view.IOrderListView
import com.qiyei.mall.ordermanager.ui.adapter.OrderListAdapter
import com.qiyei.router.path.RouteMall
import com.qiyei.router.provider.ProviderConstant
import kotlinx.android.synthetic.main.fragment_order_list.*
import org.jetbrains.anko.support.v4.toast


/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class OrderListFragment : BaseMVPFragment<OrderListPresenter>(),IOrderListView {

    /**
     * 适配器
     */
    private lateinit var mOrderListAdapter:OrderListAdapter
    /**
     * 订单状态
     */
    private var mOrderStatus:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mOrderStatus = mArgs.getInt(OrderConstant.User.KEY_ORDER_STATUS)
        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initMultiStateView(mOrderStatus)
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    /**
     * 依赖注入
     */
    override fun initComponentInject() {
        DaggerOrderManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .orderManagerModule(OrderManagerModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onOrderList(list: MutableList<Order>?) {
        if (list == null){
            return
        }
        mOrderListAdapter.datas = list
        mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
    }

    override fun onSubmitOrder(result: Boolean) {
        if (result){
            toast("提交成功")
        } else {
            toast("提交失败")
        }
    }

    override fun onConfirmOrder(result: Boolean) {
        if (result){
            toast("确认成功")
        } else {
            toast("确认失败")
        }
    }

    override fun onCancelOrder(result: Boolean) {
        if (result){
            toast("取消成功")
        } else {
            toast("取消失败")
        }
    }

    private fun initView(){
        val layoutManager = LinearLayoutManager(mContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mOrderListRecyclerView.layoutManager = layoutManager
        mOrderListAdapter = OrderListAdapter(mContext, arrayListOf())
        //监听器
        mOrderListAdapter.mListener = object:OrderListAdapter.OnClickListener{
            override fun onClick(type: Int, order: Order) {
                handleItemClick(type,order)
            }
        }
        mOrderListRecyclerView.adapter = mOrderListAdapter
    }

    private fun initMultiStateView(status:Int){
        //加载中
        mMultiStateView.viewState = MultiStateView.VIEW_STATE_LOADING
    }

    private fun loadData(){
        //获取订单
        mPresenter.getOrderList(mOrderStatus)
    }

    /**
     * 处理点击事件
     */
    private fun handleItemClick(type:Int,order: Order){
        when(type){
            //确认订单
            OrderConstant.OPT_ORDER_CONFIRM -> {
                mPresenter.confirmOrder(order.id)
            }
            //支付订单
            OrderConstant.OPT_ORDER_PAY -> {
                //跳转到去支付界面
                ARouter.getInstance().build(RouteMall.PayManager.cash_pay)
                        .withInt(ProviderConstant.KEY_ORDER_ID,order.id)
                        .withLong(ProviderConstant.KEY_ORDER_PRICE,order.totalPrice)
                        .navigation()
            }
            //取消订单
            OrderConstant.OPT_ORDER_CANCEL -> {
                mPresenter.cancelOrder(order.id)
            }
        }
    }

}
