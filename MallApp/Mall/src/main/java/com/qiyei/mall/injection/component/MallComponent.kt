package com.qiyei.mall.injection.component

import com.qiyei.framework.injection.component.ActivityComponent
import com.qiyei.framework.injection.scope.PreComponentScope
import com.qiyei.mall.ui.fragment.*
import dagger.Component

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@PreComponentScope
@Component(dependencies = arrayOf(ActivityComponent::class))
interface MallComponent {

    fun inject(fragment:HomeFragment)

}