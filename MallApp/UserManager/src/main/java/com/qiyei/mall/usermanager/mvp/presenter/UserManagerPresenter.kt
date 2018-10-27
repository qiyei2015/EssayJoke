package com.qiyei.mall.usermanager.mvp.presenter

import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.mall.usermanager.mvp.view.IUserManagerView
import com.qiyei.sdk.log.LogManager
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class UserManagerPresenter @Inject constructor():BasePresenter<IUserManagerView>() {

    override fun getTAG(): String {
        return UserManagerPresenter::class.java.simpleName
    }

    fun getString(){
        LogManager.i(getTAG(),"hello world")
    }
}