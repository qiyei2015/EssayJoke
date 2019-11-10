package com.qiyei.framework.injection.module


import com.trello.rxlifecycle3.LifecycleProvider
import dagger.Module
import dagger.Provides

/**
 * @author Created by qiyei2015 on 2018/9/25.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Module
class LifecycleProviderModule(private val provider: LifecycleProvider<*>) {

    /**
     * 提供Context
     */
    @Provides
    fun provideLifecycleProvider(): LifecycleProvider<*> {
        return provider
    }
}