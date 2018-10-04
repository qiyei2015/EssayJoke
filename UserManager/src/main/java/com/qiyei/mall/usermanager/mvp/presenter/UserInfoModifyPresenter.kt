package com.qiyei.mall.usermanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.usermanager.data.bean.UserInfo
import com.qiyei.mall.usermanager.mvp.view.IUserInfoModifyView
import com.qiyei.mall.usermanager.service.IUploadManagerService
import com.qiyei.mall.usermanager.service.IUserManagerService
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/4.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class UserInfoModifyPresenter @Inject constructor():BasePresenter<IUserInfoModifyView>(){

    @Inject
    lateinit var mUserManagerService: IUserManagerService

    @Inject
    lateinit var mUploadManagerService: IUploadManagerService

    /**
     * 获取token
     */
    fun getUploadToken(){
        mUploadManagerService.getUploadToken().execute(object : BaseObserver<String>(mView) {
            override fun onNext(t: String) {
                mView.onUploadTokenResult(t)
            }
        }, mLifecycleProvider)
    }

    /**
     * 修改用户信息
     */
    fun modifyUserInfo(userIcon:String,nickName:String,userGender:String,userSign:String){
        mUserManagerService.modifyUserInfo(userIcon,nickName,userGender,userSign).execute(object :BaseObserver<UserInfo>(mView){
            override fun onNext(t: UserInfo) {
                mView.onModifyUserInfoResult(t)
            }
        },mLifecycleProvider)
    }
}