package com.qiyei.mall.usermanager.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qiyei.framework.ui.fragment.BaseMVPFragment

import com.qiyei.mall.usermanager.R
import com.qiyei.mall.usermanager.injection.component.DaggerUserManagerComponent
import com.qiyei.mall.usermanager.mvp.presenter.UserManagerPresenter
import com.qiyei.mall.usermanager.mvp.view.IUserManagerView

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

    override fun onStart() {
        super.onStart()
        mPresenter.getString()
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
}
