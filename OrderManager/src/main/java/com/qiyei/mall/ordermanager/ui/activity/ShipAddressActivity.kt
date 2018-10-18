package com.qiyei.mall.ordermanager.ui.activity


import android.os.Bundle
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.ordermanager.R
import com.qiyei.mall.ordermanager.injection.component.DaggerOrderManagerComponent
import com.qiyei.mall.ordermanager.injection.module.AddressManagerModule
import com.qiyei.mall.ordermanager.mvp.presenter.ShipAddressPresenter
import com.qiyei.mall.ordermanager.mvp.view.IShipAddressView

class ShipAddressActivity : BaseMVPActivity<ShipAddressPresenter>(),IShipAddressView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ship_address)
        initView()
    }

    override fun initComponentInject() {
        DaggerOrderManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .addressManagerModule(AddressManagerModule())
                .build()
                .inject(this)
        mPresenter.mView = this

    }

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.address_manager))
                .build()

    }
}
