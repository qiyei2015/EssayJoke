package com.qiyei.framework


import android.content.Context
import android.support.multidex.MultiDexApplication
import com.qiyei.framework.injection.component.AppComponent
import com.qiyei.framework.injection.component.DaggerAppComponent
import com.qiyei.framework.injection.module.AppModule
import com.qiyei.sdk.SDKManager

/**
 * @author Created by qiyei2015 on 2018/9/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
open class FrameworkApplication: MultiDexApplication() {

    lateinit var mAppComponent:AppComponent

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        try {
            SDKManager.initSDK(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mAppComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}