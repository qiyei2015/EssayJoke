package com.qiyei.mall.usermanager.ui.activity


import android.os.Bundle
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.usermanager.R
import com.qiyei.mall.usermanager.injection.component.DaggerUserManagerComponent
import com.qiyei.mall.usermanager.injection.module.UserManagerModule
import com.qiyei.mall.usermanager.mvp.presenter.UserRegisterPresenter
import com.qiyei.mall.usermanager.mvp.view.IUserRegisterView
import kotlinx.android.synthetic.main.activity_user_register.*

import org.jetbrains.anko.toast

class UserRegisterActivity : BaseMVPActivity<UserRegisterPresenter>(),IUserRegisterView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)

        //anko 框架
        var value = intent.getIntExtra("key",-1)

        toast(""+value)

        //可以直接使用id,kotlin-android-extensions
        mRegisterBtn.setOnClickListener{
            mPresenter.register(mMobileEt.text.toString(),mPwdEt.text.toString(),mVerifyCodeEt.text.toString())
        }

        mVerifyCodeBtn.setOnClickListener {
            mVerifyCodeBtn.start()
        }
    }

    override fun onRegisterResult(result:String) {
        toast(result)
    }

    override fun initComponentInject() {
        //注入点
        DaggerUserManagerComponent.builder()
                .activityComponent(mActivityComponet)
                .userManagerModule(UserManagerModule())
                .build()
                .inject(this)
        mPresenter.mView = this

    }
}
