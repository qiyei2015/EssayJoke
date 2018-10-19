package com.qiyei.mall.ordermanager.service.impl

import com.qiyei.framework.extend.baseRespConvert
import com.qiyei.mall.ordermanager.data.bean.ShipAddress
import com.qiyei.mall.ordermanager.data.repository.AddressManagerRepository
import com.qiyei.mall.ordermanager.service.IAddressManagerService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class AddressManagerServiceImpl @Inject constructor(): IAddressManagerService {

    @Inject
    lateinit var mAddressRepository: AddressManagerRepository

    override fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String): Observable<String> {
        return mAddressRepository.addShipAddress(shipUserName,shipUserMobile,shipAddress).baseRespConvert()
    }

    override fun deleteShipAddress(id: Int): Observable<String> {
        return mAddressRepository.deleteShipAddress(id).baseRespConvert()
    }

    override fun editShipAddress(address: ShipAddress): Observable<String> {
        return mAddressRepository.editShipAddress(address).baseRespConvert()
    }

    override fun getShipAddressList(): Observable<MutableList<ShipAddress>?> {
        return mAddressRepository.getShipAddressList().baseRespConvert()
    }

}