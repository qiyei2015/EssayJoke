package com.qiyei.sdk.dc.impl;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/22.
 * Version: 1.0
 * Description: 数据缓存的操作接口
 */
public interface IDataBuffer {

    /**
     * 保存int
     * @param uri
     * @param value
     */
    void setInt(String uri, int value);

    /**
     * 获取int
     * @param uri
     * @return
     */
    Integer getInt(String uri);

    /**
     * 保存long
     * @param uri
     * @param value
     */
    void setLong(String uri, long value);

    /**
     * 获取long
     * @param uri
     * @return
     */
    Long getLong(String uri);

    /**
     * 保存float
     * @param uri
     * @param value
     */
    void setFloat(String uri, float value);

    /**
     * 获取float
     * @param uri
     * @return
     */
    Float getFloat(String uri);

    /**
     * 保存double
     * @param uri
     * @param value
     */
    void setDouble(String uri, double value);

    /**
     * 获取double
     * @param uri
     * @return
     */
    Double getDouble(String uri);

    /**
     * 保存char
     * @param uri
     * @param value
     */
    void setChar(String uri, char value);

    /**
     * 获取char
     * @param uri
     * @return
     */
    Character getChar(String uri);

    /**
     * 保存boolean
     * @param uri
     * @param value
     */
    void setBoolean(String uri, boolean value);

    /**
     * 获取boolean
     * @param uri
     * @return
     */
    public Boolean getBoolean(String uri);

    /**
     * 保存值
     * @param uri
     * @param value
     */
    void setString(String uri,String value);

    /**
     * 获取值
     * @return
     */
    String getString(String uri);
}
