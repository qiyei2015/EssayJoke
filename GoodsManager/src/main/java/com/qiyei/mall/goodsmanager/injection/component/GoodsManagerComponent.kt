package com.qiyei.mall.goodsmanager.injection.component

import com.qiyei.framework.injection.component.ActivityComponent
import com.qiyei.framework.injection.scope.PreComponentScope
import com.qiyei.mall.goodsmanager.ui.fragment.CategoryFragment

import dagger.Component

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class))
interface GoodsManagerComponent {

    fun inject(fragment: CategoryFragment)

}