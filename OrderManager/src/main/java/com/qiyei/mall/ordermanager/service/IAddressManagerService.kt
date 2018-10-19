package com.qiyei.mall.ordermanager.service

import com.qiyei.mall.ordermanager.data.bean.ShipAddress
import io.reactivex.Observable

/**
 * @author Created by qiyei2015 on 2018/10/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IAddressManagerService {

    /**
     * 添加收货地址
     */
    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String): Observable<String>

    /**
     * 删除收货地址
     */
    fun deleteShipAddress(id: Int): Observable<String>

    /**
     * 修改收货地址
     */
    fun editShipAddress(address: ShipAddress): Observable<String>

    /**
     * 获取收货地址列表
     */
    fun getShipAddressList(): Observable<MutableList<ShipAddress>?>
}