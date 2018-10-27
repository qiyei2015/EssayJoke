package com.qiyei.mall.ordermanager.injection.component


import com.qiyei.framework.injection.component.ActivityComponent
import com.qiyei.framework.injection.scope.PreComponentScope
import com.qiyei.mall.goodsmanager.injection.module.CartModule
import com.qiyei.mall.goodsmanager.injection.module.GoodsModule
import com.qiyei.mall.goodsmanager.ui.activity.CartActivity
import com.qiyei.mall.ordermanager.ui.fragment.CartFragment
import dagger.Component

/**
 * @author Created by qiyei2015 on 2018/10/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class),modules = arrayOf(CartModule::class))
interface CartComponent {

    fun inject(activity: CartActivity)

    fun inject(fragment: CartFragment)

}