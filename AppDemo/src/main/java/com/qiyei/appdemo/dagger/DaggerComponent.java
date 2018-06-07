package com.qiyei.appdemo.dagger;

import com.qiyei.appdemo.ui.activity.DaggerDemoActivity;

import dagger.Component;

/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 用来向需要依赖的地方注入
 */
@Component(modules = {DaggerModule.class})
public interface DaggerComponent {

    void inject(DaggerDemoActivity activity);

}
