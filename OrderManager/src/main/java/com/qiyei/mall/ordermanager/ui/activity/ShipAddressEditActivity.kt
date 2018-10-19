package com.qiyei.mall.ordermanager.ui.activity


import android.os.Bundle
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.ordermanager.R
import com.qiyei.mall.ordermanager.mvp.presenter.ShipAddressEditPresenter
import com.qiyei.mall.ordermanager.mvp.view.IShipAddressEditView

class ShipAddressEditActivity : BaseMVPActivity<ShipAddressEditPresenter>() ,IShipAddressEditView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ship_address_edit)
        initView()
    }

    override fun initComponentInject() {

    }

    override fun onAddShipAddress(address: String) {
        
    }

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.new_address))
                .build()

    }

}
