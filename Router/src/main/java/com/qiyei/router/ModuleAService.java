package com.qiyei.router;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author Created by qiyei2015 on 2018/6/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 定义ModuleA 的对外暴露的服务
 */
public interface ModuleAService extends IProvider {

    String TAG = "ModuleAService";

    /**
     * 服务
     * @param name
     */
    void sayHello(String name);
}
