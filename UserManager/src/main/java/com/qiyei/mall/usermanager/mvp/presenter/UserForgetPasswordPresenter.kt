package com.qiyei.mall.usermanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.usermanager.data.bean.UserInfo
import com.qiyei.mall.usermanager.mvp.view.IUserForgetPasswordView
import com.qiyei.mall.usermanager.service.IUserManagerService
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class UserForgetPasswordPresenter @Inject constructor():BasePresenter<IUserForgetPasswordView>() {
    @Inject
    lateinit var mUserManagerService: IUserManagerService


    override fun getTAG(): String {
        return UserForgetPasswordPresenter::class.java.simpleName
    }

    /**
     * 忘记密码
     */
    fun forgetPassword(userKey:String,verifyCode:String){
        mUserManagerService.forgetPassword(userKey,verifyCode)
                .execute(object : BaseObserver<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        if (t){
                            mView.onForgetPasswordResult("成功")
                        }else {
                            mView.onForgetPasswordResult("失败")
                        }
                    }
                },mLifecycleProvider)
    }
}