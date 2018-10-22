package com.qiyei.paymanager.mvp.presenter

import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.paymanager.mvp.view.ICashRegisterView
import com.qiyei.paymanager.service.IPayManagerService
import com.qiyei.sdk.server.base.BaseService
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class CashRegisterPresenter @Inject constructor():BasePresenter<ICashRegisterView>() {

    @Inject
    lateinit var mPayManagerService: IPayManagerService


}