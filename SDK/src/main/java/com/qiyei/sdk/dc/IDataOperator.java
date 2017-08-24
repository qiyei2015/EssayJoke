package com.qiyei.sdk.dc;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/24.
 * Version: 1.0
 * Description: 数据操作
 */
public interface IDataOperator {

    /**
     * 存储String 类型数据
     * @param uri
     * @param value
     */
    void setString(String uri,String value);

    /**
     * 获取String类型数据
     * @param uri
     * @return
     */
    String getString(String uri);

}
