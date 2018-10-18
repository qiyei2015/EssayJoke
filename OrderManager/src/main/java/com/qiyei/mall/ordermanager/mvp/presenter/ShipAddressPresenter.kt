package com.qiyei.mall.ordermanager.mvp.presenter

import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.mall.ordermanager.mvp.view.IShipAddressView
import com.qiyei.mall.ordermanager.service.IAddressManagerService
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class ShipAddressPresenter @Inject constructor():BasePresenter<IShipAddressView>() {

    @Inject
    lateinit var mAddressManagerService:IAddressManagerService



}