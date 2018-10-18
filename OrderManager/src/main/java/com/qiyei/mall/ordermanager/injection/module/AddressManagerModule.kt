package com.qiyei.mall.ordermanager.injection.module

import com.qiyei.mall.ordermanager.service.IAddressManagerService
import com.qiyei.mall.ordermanager.service.IOrderManagerService
import com.qiyei.mall.ordermanager.service.impl.AddressManagerServiceImpl
import com.qiyei.mall.ordermanager.service.impl.OrderManagerServiceImpl
import dagger.Module
import dagger.Provides

/**
 * @author Created by qiyei2015 on 2018/10/17.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Module
class AddressManagerModule {

    @Provides
    fun providerAddressManagerService(service:AddressManagerServiceImpl):IAddressManagerService{
        return service
    }
}