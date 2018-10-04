package com.qiyei.mall.usermanager.mvp.presenter

import com.qiyei.framework.mvp.presenter.BasePresenter
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



}