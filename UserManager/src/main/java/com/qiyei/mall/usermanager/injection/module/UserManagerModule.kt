package com.qiyei.mall.usermanager.injection.module

import com.qiyei.mall.usermanager.service.IUploadManagerService
import com.qiyei.mall.usermanager.service.IUserManagerService
import com.qiyei.mall.usermanager.service.impl.UploadManagerServiceImpl
import com.qiyei.mall.usermanager.service.impl.UserManagerServiceImpl
import dagger.Module
import dagger.Provides

/**
 * @author Created by qiyei2015 on 2018/9/24.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:用户管理模块Module
 */
@Module
class UserManagerModule {

    @Provides
    fun providerUploadManagerService(service: UploadManagerServiceImpl): IUploadManagerService {
        return service
    }

}