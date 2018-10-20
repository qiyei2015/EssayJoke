package com.qiyei.mall.usermanager.ui.activity

import android.os.Bundle
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.usermanager.R
import com.qiyei.mall.usermanager.injection.component.DaggerUserManagerComponent
import com.qiyei.mall.usermanager.mvp.presenter.UserSettingPresenter
import com.qiyei.mall.usermanager.mvp.view.IUserSettingView

class SettingActivity : BaseMVPActivity<UserSettingPresenter>(),IUserSettingView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initView()
    }

    override fun initComponentInject() {
        DaggerUserManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.setting))
                .build()

    }

}
