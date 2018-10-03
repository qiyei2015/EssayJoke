package com.qiyei.mall.usermanager.ui.activity

import android.os.Bundle
import android.view.View
import com.qiyei.framework.extend.enable
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.usermanager.R
import com.qiyei.mall.usermanager.injection.component.DaggerUserManagerComponent
import com.qiyei.mall.usermanager.injection.module.UserManagerModule
import com.qiyei.mall.usermanager.mvp.presenter.UserForgetPasswordPresenter
import com.qiyei.mall.usermanager.mvp.view.IUserForgetPasswordView
import kotlinx.android.synthetic.main.activity_user_forget_password.*
import org.jetbrains.anko.intentFor

class UserForgetPasswordActivity : BaseMVPActivity<UserForgetPasswordPresenter>(),IUserForgetPasswordView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_forget_password)
        initView()
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.mVerifyCodeButton -> {
                mVerifyCodeButton.start()
            }
            R.id.mNextButton -> {
                mPresenter.forgetPassword(mMobileEditText.text.toString(),mVerifyCodeEditText.text.toString())
            }
        }
    }

    override fun initComponentInject() {
        DaggerUserManagerComponent.builder()
                .activityComponent(mActivityComponet)
                .userManagerModule(UserManagerModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onForgetPasswordResult(result: String) {
        startActivity(intentFor<UserModifyPasswordActivity>(Pair("userKey",mMobileEditText.text.toString())))
    }

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.forget_password))
                .build()
        mNextButton.enable(mMobileEditText){isNextButtonAvailable()}
        mNextButton.enable(mVerifyCodeEditText){isNextButtonAvailable()}
        mVerifyCodeButton.setOnClickListener(this)
        mNextButton.setOnClickListener(this)
    }

    private fun isNextButtonAvailable():Boolean{
        return mMobileEditText.text.isNotEmpty()
                && mVerifyCodeEditText.text.isNotEmpty()
    }
}
