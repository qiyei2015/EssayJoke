package com.qiyei.mall.messagemanager.injection.module

import com.qiyei.mall.messagemanager.service.IMessageManagerService
import com.qiyei.mall.messagemanager.service.impl.MessageManagerServiceImpl
import dagger.Module
import dagger.Provides

/**
 * @author Created by qiyei2015 on 2018/10/27.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Module
class MessageManagerModule {

    @Provides
    fun providerMessageManagerService(service: MessageManagerServiceImpl):IMessageManagerService{
        return service
    }
}