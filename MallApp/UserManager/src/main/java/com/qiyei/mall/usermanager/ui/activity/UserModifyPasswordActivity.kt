package com.qiyei.mall.usermanager.ui.activity


import android.os.Bundle
import android.view.View
import com.qiyei.framework.extend.enable
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.usermanager.R
import com.qiyei.mall.usermanager.injection.component.DaggerUserManagerComponent
import com.qiyei.mall.usermanager.injection.module.UserManagerModule
import com.qiyei.mall.usermanager.mvp.presenter.UserModifyPasswordPresenter
import com.qiyei.mall.usermanager.mvp.view.IUserModifyPasswordView
import kotlinx.android.synthetic.main.activity_user_modify_password.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.toast

class UserModifyPasswordActivity : BaseMVPActivity<UserModifyPasswordPresenter>(),IUserModifyPasswordView {

    private lateinit var mUserKey:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_modify_password)
        mUserKey = intent.getStringExtra("userKey")
        initView()
    }

    override fun getTAG(): String {
        return UserModifyPasswordActivity::class.java.simpleName
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.mConfirmButton -> {
                mPresenter.modifyPassword(mUserKey,mPasswordEditText.text.toString())
            }
        }
    }

    override fun initComponentInject() {
        DaggerUserManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .userManagerModule(UserManagerModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onModifyPasswordResullt(result: String) {
        toast("修改密码成功")
        startActivity(intentFor<UserLoginActivity>().singleTop().clearTop())
    }

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle(getString(R.string.modify_password))
                .build()
        mConfirmButton.enable(mPasswordEditText){isConfirmAvailable()}
        mConfirmButton.enable(mPasswordConfirmEditText){isConfirmAvailable()}
        mConfirmButton.setOnClickListener(this)
    }

    private fun isConfirmAvailable():Boolean{
        return mPasswordEditText.text.isNotEmpty()
                && mPasswordConfirmEditText.text.isNotEmpty()
    }
}
