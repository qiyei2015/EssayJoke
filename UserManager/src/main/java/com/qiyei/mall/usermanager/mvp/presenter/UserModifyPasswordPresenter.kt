package com.qiyei.mall.usermanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.usermanager.mvp.view.IUserModifyPasswordView
import com.qiyei.mall.usermanager.service.IUserManagerService
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class UserModifyPasswordPresenter  @Inject constructor():BasePresenter<IUserModifyPasswordView>() {
    @Inject
    lateinit var mUserManagerService: IUserManagerService

    fun modifyPassword(userKey:String,password:String){
        mUserManagerService.modifyPassword(userKey,password)
                .execute(object :BaseObserver<Boolean>(mView){
                    override fun onNext(t: Boolean) {
                        if (t){
                            mView.onModifyPasswordResullt("修改成功")
                        }else {
                            mView.onModifyPasswordResullt("修改失败")
                        }
                    }
                },mLifecycleProvider)
    }
}