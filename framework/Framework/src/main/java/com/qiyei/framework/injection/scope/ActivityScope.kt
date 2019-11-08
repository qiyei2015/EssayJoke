package com.qiyei.framework.injection.scope

import java.lang.annotation.RetentionPolicy
import javax.inject.Scope
import java.lang.annotation.Retention
/**
 * @author Created by qiyei2015 on 2018/9/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 注解，与java的注解定义类似
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class ActivityScope