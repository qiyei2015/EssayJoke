package com.qiyei.framework.injection.scope

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

/**
 * @author Created by qiyei2015 on 2018/9/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 用于业务组件
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class PreComponentScope