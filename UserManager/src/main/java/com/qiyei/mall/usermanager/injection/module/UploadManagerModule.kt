package com.qiyei.mall.usermanager.injection.module

import com.qiyei.mall.usermanager.service.IUserManagerService
import com.qiyei.mall.usermanager.service.impl.UserManagerServiceImpl
import dagger.Module
import dagger.Provides

/**
 * @author Created by qiyei2015 on 2018/10/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Module
class UploadManagerModule {

    @Provides
    fun providerUserManagerService(service: UserManagerServiceImpl): IUserManagerService {
        return service
    }
}