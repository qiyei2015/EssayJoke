package com.qiyei.mall.ordermanager.mvp.view

import com.qiyei.framework.mvp.view.IBaseView
import com.qiyei.mall.ordermanager.data.bean.ShipAddress

/**
 * @author Created by qiyei2015 on 2018/10/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IShipAddressView : IBaseView {

    /**
     * 地址回调
     */
    fun onShipAddressList(list: MutableList<ShipAddress>?)

    fun onUpdateShipAddress(result:Boolean)

    fun onDeleteShipAddress(result:Boolean)
}