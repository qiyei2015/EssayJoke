package com.qiyei.sdk.dc.impl;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/22.
 * Version: 1.0
 * Description: 数据缓存的操作接口
 */
public interface IDataBuffer {

    /**
     * 初始化接口
     */
    void init();

    /**
     * 获取Value
     * @param key
     * @return
     */
    String getValue(String key);

    /**
     * 存储Value
     * @param key
     * @param value
     * @return
     */
    boolean setValue(String key,String value);


}
