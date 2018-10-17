package com.qiyei.mall.ordermanager.data.bean

import android.os.Parcelable


/**
 * @author Created by qiyei2015 on 2018/10/17.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */

abstract class ShipAddress(val id: Int,
                       var shipUserName: String,
                       var shipUserMobile: String,
                       var shipAddress: String,
                       var shipIsDefault: Int):Parcelable {
}