package com.qiyei.appdemo.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 提供模块依赖
 */
@Module
public class DaggerModule {
    /**
     * 对外提供UserModel
     * @return
     */
    @Provides
    public UserModel provideUserModel(){
        return new UserModel();
    }


}
