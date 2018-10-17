package com.qiyei.mall.ordermanager.injection.component


import com.qiyei.framework.injection.component.ActivityComponent
import com.qiyei.framework.injection.scope.PreComponentScope
import com.qiyei.mall.ordermanager.injection.module.OrderManagerModule
import com.qiyei.mall.ordermanager.ui.activity.OrderConfirmActivity
import com.qiyei.mall.ordermanager.ui.fragment.OrderFragment
import dagger.Component

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(OrderManagerModule::class))
interface OrderManagerComponent {


    fun inject(fragment: OrderFragment)

    fun inject(activity:OrderConfirmActivity)
}