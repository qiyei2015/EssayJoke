package com.qiyei.paymanager.mvp.view

import com.qiyei.framework.mvp.view.IBaseView

/**
 * @author Created by qiyei2015 on 2018/10/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface ICashRegisterView :IBaseView{

    fun onPaySignCallback(sign:String)

    fun onPayOrderCallback(result: Boolean)
}