package com.qiyei.framework.injection.component

import android.app.Activity
import android.content.Context
import com.qiyei.framework.injection.module.ActivityModule
import com.qiyei.framework.injection.module.LifecycleProviderModule
import com.qiyei.framework.injection.scope.ActivityScope
import com.trello.rxlifecycle3.LifecycleProvider
import dagger.Component


/**
 * @author Created by qiyei2015 on 2018/9/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: Activity的Component，依赖AppComponent
 */
@ActivityScope
@Component(modules = arrayOf(ActivityModule::class,LifecycleProviderModule::class),dependencies = arrayOf(AppComponent::class))
interface ActivityComponent {

    /**
     * 提供Activity
     */
    fun activity():Activity

    /**
     * 提供Context
     */
    fun context(): Context

    /**
     * 提供LifecycleProvider
     */
    fun lifecycleProvider():LifecycleProvider<*>

}