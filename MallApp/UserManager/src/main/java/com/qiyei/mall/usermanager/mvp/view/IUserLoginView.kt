package com.qiyei.mall.usermanager.mvp.view

import com.qiyei.framework.mvp.view.IBaseView
import com.qiyei.mall.usermanager.data.bean.UserInfo

/**
 * @author Created by qiyei2015 on 2018/10/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:登录View
 */
interface IUserLoginView:IBaseView {
    /**
     * 登录回调
     * @param userInfo
     */
    fun onLoginResult(userInfo:UserInfo)
}