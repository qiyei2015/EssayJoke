package com.qiyei.mall.usermanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.usermanager.mvp.view.IUserRegisterView
import com.qiyei.mall.usermanager.service.impl.UserManagerServiceImpl

/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 用户注册界面的Presenter
 */
open class UserRegisterPresenter : BasePresenter<IUserRegisterView>() {

    /**
     * 用户注册
     * @param userKey 用户标志，可以是手机，邮箱，或者用户名
     * @param password 用户密码
     * @param verifyCode 验证码
     */
    fun register(userKey: String, password: String, verifyCode: String) {

        //业务实现
        var userManagerService = UserManagerServiceImpl()
        userManagerService.register(userKey, password, verifyCode)
                .execute(object :BaseObserver<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        mView.onRegisterResult(t)
                    }
                })
    }
}