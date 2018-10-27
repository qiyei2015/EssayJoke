package com.qiyei.mall.usermanager.mvp.view

import com.qiyei.framework.mvp.view.IBaseView

/**
 * @author Created by qiyei2015 on 2018/10/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IUserForgetPasswordView:IBaseView{

    /**
     * 忘记密码
     */
    fun onForgetPasswordResult(result:String)

}