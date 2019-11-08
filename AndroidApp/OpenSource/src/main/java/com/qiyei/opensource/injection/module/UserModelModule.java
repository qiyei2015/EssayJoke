package com.qiyei.opensource.injection.module;

import com.qiyei.opensource.data.bean.UserModel;

import dagger.Module;
import dagger.Provides;

/**
 * @author Created by qiyei2015 on 2018/10/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Module
public class UserModelModule {

    @Provides
    public UserModel providerUserModel(){
        return new UserModel();
    }
}
