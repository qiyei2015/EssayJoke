package com.qiyei.paymanager.injection.module

import com.qiyei.paymanager.service.IPayManagerService
import com.qiyei.paymanager.service.impl.PayManagerServiceImpl
import dagger.Module
import dagger.Provides

/**
 * @author Created by qiyei2015 on 2018/10/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Module
class PayManagerModule {

    @Provides
    fun providerPayManagerService(service:PayManagerServiceImpl):IPayManagerService{
        return service
    }
}