package com.qiyei.paymanager.injection.component

import com.qiyei.framework.injection.component.ActivityComponent
import com.qiyei.framework.injection.scope.PreComponentScope
import com.qiyei.paymanager.injection.module.PayManagerModule
import com.qiyei.paymanager.ui.activity.CashRegisterActivity
import dagger.Component

/**
 * @author Created by qiyei2015 on 2018/10/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(PayManagerModule::class))
interface PayManagerComponent {

    fun inject(activity:CashRegisterActivity)
}