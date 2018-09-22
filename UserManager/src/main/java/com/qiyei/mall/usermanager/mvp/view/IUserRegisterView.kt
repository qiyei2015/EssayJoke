package com.qiyei.mall.usermanager.mvp.view

import com.qiyei.framework.mvp.view.IBaseView

/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:用户注册界面的View层
 */
interface IUserRegisterView : IBaseView{

    /**
     * 注册结果回调
     * @param success
     */
    fun onRegisterResult(success: Boolean)
}