package com.qiyei.framework.injection.module

import android.app.Activity
import android.content.Context
import com.qiyei.framework.injection.scope.ActivityScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Created by qiyei2015 on 2018/9/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Module
class ActivityModule(private val activity:Activity) {

    /**
     * 提供Context
     */
    @Provides
    @ActivityScope //描述范围
    fun provideActivity():Activity{
        return activity
    }

}