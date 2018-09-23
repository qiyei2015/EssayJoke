package com.qiyei.mall

import android.app.Application
import com.qiyei.sdk.SDKManager

/**
 * @author Created by qiyei2015 on 2018/9/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class MallApplication :Application() {

    override fun onCreate() {
        super.onCreate()
//        try {
//            SDKManager.initSDK(this)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

}