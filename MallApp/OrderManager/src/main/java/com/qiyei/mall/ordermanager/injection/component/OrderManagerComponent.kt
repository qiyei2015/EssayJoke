package com.qiyei.mall.ordermanager.injection.component


import com.qiyei.framework.injection.component.ActivityComponent
import com.qiyei.framework.injection.scope.PreComponentScope
import com.qiyei.mall.ordermanager.injection.module.AddressManagerModule
import com.qiyei.mall.ordermanager.injection.module.OrderManagerModule
import com.qiyei.mall.ordermanager.ui.activity.*
import com.qiyei.mall.ordermanager.ui.fragment.OrderListFragment
import dagger.Component

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(OrderManagerModule::class,AddressManagerModule::class))
interface OrderManagerComponent {


    fun inject(fragment: OrderListFragment)

    fun inject(activity:OrderConfirmActivity)

    fun inject(activity:OrderListActivity)

    fun inject(activity:OrderDetailActivity)

    fun inject(activity:ShipAddressActivity)

    fun inject(activity:ShipAddressEditActivity)

}