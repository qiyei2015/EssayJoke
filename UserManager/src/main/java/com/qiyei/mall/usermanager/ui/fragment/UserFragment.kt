package com.qiyei.mall.usermanager.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qiyei.framework.extend.onClick
import com.qiyei.framework.ui.fragment.BaseMVPFragment

import com.qiyei.mall.usermanager.R
import com.qiyei.mall.usermanager.injection.component.DaggerUserManagerComponent
import com.qiyei.mall.usermanager.mvp.presenter.UserManagerPresenter
import com.qiyei.mall.usermanager.mvp.view.IUserManagerView
import com.qiyei.mall.usermanager.ui.activity.UserLoginActivity
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

    override fun getTAG(): String {
        return UserFragment::class.java.simpleName
    }

    /*
    点击事件
 */
    override fun onClick(view: View) {
        when (view.id) {
//            R.id.mUserIconIv, R.id.mUserNameTv -> {
//                afterLogin {
//                    startActivity<UserInfoActivity>()
//                }
//            }
//
//            R.id.mWaitPayOrderTv -> {
//                startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_PAY)
//            }
//            R.id.mWaitConfirmOrderTv -> {
//                startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_CONFIRM)
//            }
//            R.id.mCompleteOrderTv -> {
//                startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_COMPLETED)
//            }
//            R.id.mAllOrderTv -> {
//                afterLogin {
//                    startActivity<OrderActivity>()
//                }
//            }
//
//            R.id.mAddressTv -> {
//                afterLogin {
//                    startActivity<ShipAddressActivity>()
//                }
//            }
//            R.id.mShareTv -> {
//                toast(R.string.coming_soon_tip)
//            }
//            R.id.mSettingTv -> {
//                startActivity<SettingActivity>()
//            }
        }
    }

    private fun initView(){
        mUserIconIv.onClick(this)
        mUserNameTv.onClick(this)

        mWaitPayOrderTv.onClick(this)
        mWaitConfirmOrderTv.onClick(this)
        mCompleteOrderTv.onClick(this)
        mAllOrderTv.onClick(this)
        mAddressTv.onClick(this)
        mShareTv.onClick(this)
        mSettingTv.onClick(this)
    }


}
