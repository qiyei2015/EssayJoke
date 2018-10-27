package com.qiyei.mall.ordermanager.data.repository

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.framework.net.RetrofitFactory
import com.qiyei.mall.ordermanager.data.api.IAddressManagerApi
import com.qiyei.mall.ordermanager.data.bean.ShipAddress
import com.qiyei.mall.ordermanager.data.protocol.*
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/17.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class AddressManagerRepository @Inject constructor() {

    /**
     * 添加收货地址
     */
    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String): Observable<BaseResp<String>> {
        return RetrofitFactory.INSTANCE.create(IAddressManagerApi::class.java).addShipAddress(AddShipAddressReq(shipUserName,shipUserMobile,shipAddress))
    }

    /**
     * 删除收货地址
     */
    fun deleteShipAddress(id: Int): Observable<BaseResp<String>> {
        return RetrofitFactory.INSTANCE.create(IAddressManagerApi::class.java).deleteShipAddress(DeleteShipAddressReq(id))
    }

    /**
     * 修改收货地址
     */
    fun editShipAddress(address:ShipAddress): Observable<BaseResp<String>> {
        return RetrofitFactory.INSTANCE.create(IAddressManagerApi::class.java).editShipAddress(EditShipAddressReq(address.id,address.shipUserName,address.shipUserMobile,address.shipAddress,address.shipIsDefault))
    }

    /**
     * 获取收货地址列表
     */
    fun getShipAddressList(): Observable<BaseResp<MutableList<ShipAddress>?>> {
        return RetrofitFactory.INSTANCE.create(IAddressManagerApi::class.java).getShipAddressList()
    }
}