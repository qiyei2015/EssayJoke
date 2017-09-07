package com.qiyei.sdk.server.base;

import android.os.IBinder;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/9/7.
 * Version: 1.0
 * Description: Binder连接池
 */
public interface IBinderPool {

    /**
     * 添加Binder
     * @param name
     * @param binder
     */
    void addBinder(String name, IBinder binder);

    /**
     * 移除Binder
     * @param name
     */
    void removeBinder(String name);

    /**
     * 根据名称查询服务
     * @param name
     * @return
     */
    IBinder queryBinder(String name);



}
