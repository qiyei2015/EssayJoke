package com.qiyei.mall.ordermanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.ordermanager.data.bean.ShipAddress
import com.qiyei.mall.ordermanager.mvp.view.IShipAddressEditView
import com.qiyei.mall.ordermanager.mvp.view.IShipAddressView
import com.qiyei.mall.ordermanager.service.IAddressManagerService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class ShipAddressEditPresenter @Inject constructor():BasePresenter<IShipAddressEditView>() {

    @Inject
    lateinit var mAddressManagerService:IAddressManagerService

    /**
     * 添加收货地址
     */
    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String){
        mAddressManagerService.addShipAddress(shipUserName,shipUserMobile,shipAddress).execute(object :BaseObserver<Boolean>(mView){
            override fun onNext(item: Boolean) {
                mView.onAddShipAddress(item)
            }
        },mLifecycleProvider)
    }

}