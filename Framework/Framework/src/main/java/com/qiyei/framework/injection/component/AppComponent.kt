package com.qiyei.framework.injection.component

import android.content.Context
import com.qiyei.framework.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author Created by qiyei2015 on 2018/9/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 全局的Component
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun context():Context
}