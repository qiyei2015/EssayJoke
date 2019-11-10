package com.qiyei.framework.injection.module

import android.content.Context
import com.qiyei.framework.FrameworkApplication
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
class AppModule(private val context: FrameworkApplication) {

    /**
     * 提供Context
     */
    @Provides
    @Singleton //单例
    fun provideContext():Context{
        return context
    }

}