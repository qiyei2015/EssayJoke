package com.qiyei.mall.ordermanager.ui.activity


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.kennyc.view.MultiStateView
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.ordermanager.R
import com.qiyei.mall.ordermanager.data.bean.ShipAddress
import com.qiyei.mall.ordermanager.injection.component.DaggerOrderManagerComponent
import com.qiyei.mall.ordermanager.injection.module.AddressManagerModule
import com.qiyei.mall.ordermanager.mvp.presenter.ShipAddressPresenter
import com.qiyei.mall.ordermanager.mvp.view.IShipAddressView
import com.qiyei.mall.ordermanager.ui.adapter.ShipAddressAdapter
import com.qiyei.sdk.log.LogManager
import kotlinx.android.synthetic.main.activity_ship_address.*
import org.jetbrains.anko.startActivity

class ShipAddressActivity : BaseMVPActivity<ShipAddressPresenter>(),IShipAddressView {

    /**
     * 地址适配器
     */
    private lateinit var mShipAddressAdapter:ShipAddressAdapter

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

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.address_manager))
                .build()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mAddressRecyclerView.layoutManager = layoutManager
        mShipAddressAdapter = ShipAddressAdapter(this, arrayListOf())
        mAddressRecyclerView.adapter = mShipAddressAdapter
        mAddAddressButton.setOnClickListener(this)
    }

    private fun loadData(){
        mPresenter.getShipAddressList()
    }
}
