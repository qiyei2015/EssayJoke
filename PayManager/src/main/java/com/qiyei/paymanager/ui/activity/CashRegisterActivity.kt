package com.qiyei.paymanager.ui.activity


import android.os.Bundle
import android.view.View
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.paymanager.R
import com.qiyei.paymanager.injection.component.DaggerPayManagerComponent
import com.qiyei.paymanager.injection.module.PayManagerModule
import com.qiyei.paymanager.mvp.presenter.CashRegisterPresenter
import com.qiyei.paymanager.mvp.view.ICashRegisterView

class CashRegisterActivity : BaseMVPActivity<CashRegisterPresenter>(),ICashRegisterView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_register)
        initView()
    }

    override fun initComponentInject() {
        DaggerPayManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .payManagerModule(PayManagerModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onClick(view: View) {
        super.onClick(view)
    }



    private fun initView(){

    }
}
