package com.qiyei.framework.injection.component

import android.app.Activity
import com.qiyei.framework.injection.module.ActivityModule
import com.qiyei.framework.injection.scope.ActivityScope
import dagger.Component


/**
 * @author Created by qiyei2015 on 2018/9/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: Activity的Component，依赖AppComponent
 */
@ActivityScope
@Component(modules = arrayOf(ActivityModule::class),dependencies = arrayOf(AppComponent::class))
interface ActivityComponent {
    fun activity():Activity
}