package com.qiyei.mall.usermanager.ui.activity



import android.os.Bundle
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.usermanager.R
import com.qiyei.mall.usermanager.data.bean.UserInfo
import com.qiyei.mall.usermanager.injection.component.DaggerUserManagerComponent
import com.qiyei.mall.usermanager.injection.module.UploadManagerModule
import com.qiyei.mall.usermanager.injection.module.UserManagerModule
import com.qiyei.mall.usermanager.mvp.presenter.UserInfoModifyPresenter
import com.qiyei.mall.usermanager.mvp.view.IUserInfoModifyView

class UserInfoModifyActivity : BaseMVPActivity<UserInfoModifyPresenter>(),IUserInfoModifyView {

    private lateinit var mUserInfo: UserInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info_modify)
//        mUserInfo = intent.extras["userInfo"] as UserInfo

        initView()
    }

    override fun initComponentInject() {
        //注入点
        DaggerUserManagerComponent.builder()
                .activityComponent(mActivityComponet)
                .userManagerModule(UserManagerModule())
                .uploadManagerModule(UploadManagerModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    private fun initView(){

    }

}
