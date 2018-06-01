package com.qiyei.appdemo.model;

import com.qiyei.sdk.log.LogManager;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/7/30.
 * Version: 1.0
 * Description:
 */
public class Control implements IControl {

    @Override
    public void printHello(String msg) {
        LogManager.d("daili",msg);
    }
}
