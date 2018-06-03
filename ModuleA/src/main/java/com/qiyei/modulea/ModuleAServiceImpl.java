package com.qiyei.modulea;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qiyei.router.ModuleAService;
import com.qiyei.sdk.log.LogManager;

/**
 * @author Created by qiyei2015 on 2018/6/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 服务事项，由各个Module实现
 */
@Route(path = "/service/ModuleAService", name = "测试服务")
public class ModuleAServiceImpl implements ModuleAService {


    @Override
    public void sayHello(String name) {
        LogManager.i(TAG,"name:" + name);
    }

    @Override
    public void init(Context context) {
        LogManager.i(TAG,"context:" + context);
    }


}
