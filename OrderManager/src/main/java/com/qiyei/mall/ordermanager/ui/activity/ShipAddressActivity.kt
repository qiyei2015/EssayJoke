package com.qiyei.mall.ordermanager.ui.activity


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bigkoo.alertview.AlertView
import com.kennyc.view.MultiStateView
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.ordermanager.R
import com.qiyei.mall.ordermanager.common.OrderConstant
import com.qiyei.mall.ordermanager.data.bean.ShipAddress
import com.qiyei.mall.ordermanager.injection.component.DaggerOrderManagerComponent
import com.qiyei.mall.ordermanager.injection.module.AddressManagerModule
import com.qiyei.mall.ordermanager.mvp.presenter.ShipAddressPresenter
import com.qiyei.mall.ordermanager.mvp.view.IShipAddressView
import com.qiyei.mall.ordermanager.ui.adapter.ShipAddressAdapter
import com.qiyei.sdk.log.LogManager
import kotlinx.android.synthetic.main.activity_ship_address.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class ShipAddressActivity : BaseMVPActivity<ShipAddressPresenter>(),IShipAddressView {

    /**
     * 地址适配器
     */
    private lateinit var mShipAddressAdapter:ShipAddressAdapter
    /**
     * 默认地址
     */
    private lateinit var mDefaultAddress:ShipAddress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ship_address)
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun initComponentInject() {
        DaggerOrderManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .addressManagerModule(AddressManagerModule())
                .build()
                .inject(this)
        mPresenter.mView = this

    }

    override fun onClick(view: View) {
        super.onClick(view)
        when(view.id){
            R.id.mAddAddressButton -> {
                startActivity<ShipAddressEditActivity>()
            }
        }
    }

    override fun onShipAddressList(list: MutableList<ShipAddress>?) {
        LogManager.i(getTAG(),"onShipAddressList,list:$list")
        if (list == null){
            return
        }
        mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        mShipAddressAdapter.datas = list
        LogManager.i(getTAG(),"onShipAddressList,mShipAddressAdapter.datas:${mShipAddressAdapter.datas}")
    }

    override fun onUpdateShipAddress(result: Boolean) {
        if (result){
            toast("保存成功")
            loadData()
        }else {
            toast("保存失败")
        }
    }

    override fun onDeleteShipAddress(result: Boolean) {
        if (result){
            toast("删除成功")
            loadData()
        }else {
            toast("删除失败")
        }
    }

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.address_manager))
                .setRightText(getString(R.string.common_save))
                .setRightClickListener {
                    if (mDefaultAddress != null){
                        mPresenter.editShipAddress(mDefaultAddress)
                    }
                }
                .build()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mAddressRecyclerView.layoutManager = layoutManager
        mShipAddressAdapter = ShipAddressAdapter(this, arrayListOf())
        mShipAddressAdapter.mShipAddressListener = ShipAddresslistener()
        mAddressRecyclerView.adapter = mShipAddressAdapter
        mAddAddressButton.setOnClickListener(this)
    }

    private fun loadData(){
        mPresenter.getShipAddressList()
    }

    /**
     * 内部类监听
     */
    inner class ShipAddresslistener:ShipAddressAdapter.ShipAddressListener{
        override fun onSetDefault(address: ShipAddress) {
            LogManager.i(getTAG(),"onSetDefault,address:$address")
            mDefaultAddress = address
        }

        override fun onEdit(address: ShipAddress) {
            LogManager.i(getTAG(),"onEdit,address:$address")
            startActivity<ShipAddressEditActivity>(OrderConstant.ADDRESS_ID to address)
        }

        override fun onDelete(address: ShipAddress) {
            LogManager.i(getTAG(),"onDelete,address:$address")
            AlertView.Builder().setContext(mContext)
                    .setStyle(AlertView.Style.Alert)
                    .setTitle("删除")
                    .setDestructive("确定")
                    .setMessage("确认删除该地址?")
                    .setCancelText("取消")
                    .setOnItemClickListener { o, position ->
                        //确认按钮
                        if (position == 0){
                            mPresenter.deleteShipAddress(address.id)
                        }
                    }
                    .setOthers(null)
                    .build()
                    .show()
        }

    }
}
