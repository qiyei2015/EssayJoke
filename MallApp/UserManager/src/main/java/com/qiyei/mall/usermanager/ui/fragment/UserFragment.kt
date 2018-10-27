package com.qiyei.mall.usermanager.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.qiyei.framework.constant.MallConstant
import com.qiyei.framework.constant.OrderConstant
import com.qiyei.framework.constant.OrderStatus
import com.qiyei.framework.extend.loadUrl
import com.qiyei.framework.extend.onClick
import com.qiyei.framework.ui.fragment.BaseMVPFragment

import com.qiyei.mall.usermanager.R
import com.qiyei.mall.usermanager.injection.component.DaggerUserManagerComponent
import com.qiyei.mall.usermanager.mvp.presenter.UserManagerPresenter
import com.qiyei.mall.usermanager.mvp.view.IUserManagerView
import com.qiyei.mall.usermanager.ui.activity.SettingActivity
import com.qiyei.mall.usermanager.ui.activity.UserInfoActivity
import com.qiyei.provider.common.afterLogin
import com.qiyei.provider.common.isLogin
import com.qiyei.provider.router.RouteMall
import com.qiyei.sdk.dc.DataManager
import kotlinx.android.synthetic.main.fragment_user.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class UserFragment : BaseMVPFragment<UserManagerPresenter>(),IUserManagerView {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    /**
     * 依赖注入
     */
    override fun initComponentInject() {
        DaggerUserManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    /**
     * 点击事件
     */
    override fun onClick(view: View) {
        when (view.id) {
            R.id.mUserIconImageView, R.id.mUserNameTextView -> {
                afterLogin {
                    startActivity<UserInfoActivity>()
                }
            }

            R.id.mWaitPayOrderTextView -> {
                ARouter.getInstance()
                        .build(RouteMall.OrderManager.ORDER_LIST)
                        .withInt(OrderConstant.User.KEY_ORDER_STATUS, OrderStatus.ORDER_WAIT_PAY)
                        .navigation()
            }
            R.id.mWaitConfirmOrderTextView -> {
                ARouter.getInstance()
                        .build(RouteMall.OrderManager.ORDER_LIST)
                        .withInt(OrderConstant.User.KEY_ORDER_STATUS,OrderStatus.ORDER_WAIT_CONFIRM)
                        .navigation()
            }
            R.id.mCompleteOrderTextView -> {
                ARouter.getInstance()
                        .build(RouteMall.OrderManager.ORDER_LIST)
                        .withInt(OrderConstant.User.KEY_ORDER_STATUS,OrderStatus.ORDER_COMPLETED)
                        .navigation()
            }
            R.id.mAllOrderTextView-> {
                afterLogin {
                    ARouter.getInstance()
                            .build(RouteMall.OrderManager.ORDER_LIST)
                            .navigation()
                }
            }
            R.id.mAddressTextView -> {
                afterLogin {
//                    startActivity<ShipAddressActivity>()
                }
            }
            R.id.mShareTextView -> {
                toast(R.string.coming_soon_tip)
            }
            R.id.mSettingTextView -> {
                startActivity<SettingActivity>()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        updateUserInfoView()
    }

    private fun initView(){
        mUserIconImageView.onClick(this)
        mUserNameTextView.onClick(this)
        mWaitPayOrderTextView.onClick(this)
        mWaitConfirmOrderTextView.onClick(this)
        mCompleteOrderTextView.onClick(this)
        mAllOrderTextView.onClick(this)
        mAddressTextView.onClick(this)
        mShareTextView.onClick(this)
        mSettingTextView.onClick(this)
    }

    /**
     * 更新用户信息显示
     */
    private fun updateUserInfoView(){
        //如果已登录
        if (isLogin()){
            val name = DataManager.getInstance().getString(MallConstant::class.java, MallConstant.KEY_USER_NAME,"")
            mUserNameTextView.text = name
            val url = DataManager.getInstance().getString(MallConstant::class.java, MallConstant.KEY_USER_ICON,null)
            if (url != null){
                mUserIconImageView.loadUrl(url)
            }
        }else {
            mUserNameTextView.text = getString(R.string.un_login_text)
            mUserIconImageView.setImageResource(R.drawable.icon_default_user)
        }
    }
}
