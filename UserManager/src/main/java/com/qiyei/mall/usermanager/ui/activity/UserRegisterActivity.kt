package com.qiyei.mall.usermanager.ui.activity


import android.os.Bundle
import android.view.View
import com.qiyei.framework.extend.enable
import com.qiyei.framework.titlebar.CommonTitleBar
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

        initView()
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.mRegisterBtn -> {
                mPresenter.register(mMobileEt.text.toString(),mPwdEt.text.toString(),mVerifyCodeEt.text.toString())
            }
            R.id.mVerifyCodeBtn -> {
                mVerifyCodeBtn.start()
            }
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

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle("注册")
                .build()
        //lambda表达式移到括号外面了
        mRegisterBtn.enable(mMobileEt){isRegisterEnable()}
        mRegisterBtn.enable(mVerifyCodeEt){isRegisterEnable()}
        mRegisterBtn.enable(mPwdEt){isRegisterEnable()}
        mRegisterBtn.enable(mPwdConfirmEt){isRegisterEnable()}
        //可以直接使用id
        mRegisterBtn.setOnClickListener(this)
        mVerifyCodeBtn.setOnClickListener(this)
    }

    /**
     * 注册按钮是否可用
     */
    private fun isRegisterEnable():Boolean{
        return mMobileEt.text.isNotEmpty()
                && mVerifyCodeEt.text.isNotEmpty()
                && mPwdEt.text.isNotEmpty()
                && mPwdConfirmEt.text.isNotEmpty()
    }
}
