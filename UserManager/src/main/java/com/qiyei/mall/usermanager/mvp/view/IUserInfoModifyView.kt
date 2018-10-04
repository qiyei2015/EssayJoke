package com.qiyei.mall.usermanager.mvp.view

import com.qiyei.framework.mvp.view.IBaseView
import com.qiyei.mall.usermanager.data.bean.UserInfo

/**
 * @author Created by qiyei2015 on 2018/10/4.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IUserInfoModifyView:IBaseView {

    fun onUploadTokenResult(result:String)

    fun onModifyUserInfoResult(userInfo:UserInfo)

}