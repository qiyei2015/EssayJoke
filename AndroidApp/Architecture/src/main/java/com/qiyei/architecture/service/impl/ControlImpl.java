package com.qiyei.architecture.service.impl;

import com.qiyei.architecture.service.IControlService;
import com.qiyei.sdk.log.LogManager;

/**
 * @author Created by qiyei2015 on 2018/11/19.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ControlImpl implements IControlService {

    @Override
    public void printHello(String msg) {
        LogManager.d("daili",msg);
    }

}
