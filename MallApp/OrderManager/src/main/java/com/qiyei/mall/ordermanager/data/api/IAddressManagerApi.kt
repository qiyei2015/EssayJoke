package com.qiyei.mall.ordermanager.data.api

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.mall.ordermanager.data.bean.ShipAddress
import com.qiyei.mall.ordermanager.data.protocol.AddShipAddressReq
import com.qiyei.mall.ordermanager.data.protocol.DeleteShipAddressReq
import com.qiyei.mall.ordermanager.data.protocol.EditShipAddressReq
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * @author Created by qiyei2015 on 2018/10/19.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IAddressManagerApi {

    /**
     * 添加收货地址
     */
    @POST("shipAddress/add")
    fun addShipAddress(@Body req: AddShipAddressReq): Observable<BaseResp<String>>

    /**
     * 删除收货地址
     */
    @POST("shipAddress/delete")
    fun deleteShipAddress(@Body req: DeleteShipAddressReq): Observable<BaseResp<String>>

    /**
     * 修改收货地址
     */
    @POST("shipAddress/modify")
    fun editShipAddress(@Body req: EditShipAddressReq): Observable<BaseResp<String>>

    /**
     * 查询收货地址列表
     */
    @POST("shipAddress/getList")
    fun getShipAddressList(): Observable<BaseResp<MutableList<ShipAddress>?>>

}