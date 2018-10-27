package com.qiyei.mall.ordermanager.data.protocol

/**
 * @author Created by qiyei2015 on 2018/10/17.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
data class EditShipAddressReq(val id:Int,val shipUserName:String,val shipUserMobile:String,val shipAddress:String,
                         val shipIsDefault:Int) {
}