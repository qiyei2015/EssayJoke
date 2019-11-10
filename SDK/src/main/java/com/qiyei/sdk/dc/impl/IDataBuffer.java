package com.qiyei.sdk.dc.impl;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/22.
 * Version: 1.0
 * Description: 数据缓存的操作接口
 */
public interface IDataBuffer {

    /**
     * 保存值
     * @param uri
     * @param value
     */
    void setValue(String uri,String value);

    /**
     * 读取值
     * @param uri
     * @return
     */
    String getValue(String uri);

    /**
     * 删除指定uri的数据
     * @param uri
     */
    void deleteValue(String uri);

}
