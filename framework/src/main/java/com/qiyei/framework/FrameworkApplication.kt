package com.qiyei.framework

import android.app.Application
import android.content.Context
import com.qiyei.framework.injection.component.AppComponent
import com.qiyei.framework.injection.component.DaggerAppComponent
import com.qiyei.framework.injection.module.AppModule

/**
 * @author Created by qiyei2015 on 2018/9/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
open class FrameworkApplication:Application() {

    lateinit var mAppComponent:AppComponent

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()

        mAppComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

}