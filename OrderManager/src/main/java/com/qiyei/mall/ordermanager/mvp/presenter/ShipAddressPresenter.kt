package com.qiyei.mall.ordermanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.ordermanager.data.bean.ShipAddress
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
class ShipAddressPresenter @Inject constructor():BasePresenter<IShipAddressView>() {

    @Inject
    lateinit var mAddressManagerService:IAddressManagerService

    /**
     * 获取收货地址列表
     */
    fun getShipAddressList(){
        mAddressManagerService.getShipAddressList().execute(object : BaseObserver<MutableList<ShipAddress>?>(mView){
            override fun onNext(item: MutableList<ShipAddress>?) {
                mView.onShipAddressList(item)
            }
        },mLifecycleProvider)
    }

    /**
     * 修改收货地址
     */
    fun editShipAddress(address: ShipAddress){
        mAddressManagerService.editShipAddress(address).execute(object :BaseObserver<Boolean>(mView){
            override fun onNext(item: Boolean) {
                mView.onUpdateShipAddress(item)
            }
        },mLifecycleProvider)
    }

    /**
     * 删除收货地址
     */
    fun deleteShipAddress(id: Int){
        mAddressManagerService.deleteShipAddress(id).execute(object : BaseObserver<Boolean>(mView){
            override fun onNext(item: Boolean) {
                mView.onDeleteShipAddress(item)
            }
        },mLifecycleProvider)
    }


}