package com.qiyei.mall.usermanager.mvp.view

import com.qiyei.framework.mvp.view.IBaseView

/**
 * @author Created by qiyei2015 on 2018/10/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IUserModifyPasswordView:IBaseView {

    /**
     * 重置密码
     */
    fun onModifyPasswordResullt(result:String)
}