package com.qiyei.mall.usermanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.usermanager.data.bean.UserInfo
import com.qiyei.mall.usermanager.mvp.view.IUserLoginView
import com.qiyei.mall.usermanager.service.IUserManagerService
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
open class UserLoginPresenter @Inject constructor():BasePresenter<IUserLoginView>(){

    @Inject
    lateinit var mUserManagerService: IUserManagerService

    /**
     * 用户注册
     * @param userKey 用户标志，可以是手机，邮箱，或者用户名
     * @param password 用户密码
     * @param pushId pushId
     */
    fun login(userKey: String, password: String, pushId: String) {
        mUserManagerService.login(userKey, password, pushId)
                .execute(object : BaseObserver<UserInfo>(mView){
                    override fun onNext(t: UserInfo) {
                        mView.onLoginResult(t)
                    }
                },mLifecycleProvider)
    }
}