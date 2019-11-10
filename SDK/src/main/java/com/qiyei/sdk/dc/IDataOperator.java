package com.qiyei.sdk.dc;


/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/24.
 * Version: 1.0
 * Description: 数据操作
 */
public interface IDataOperator {

    /**
     * 获取uri
     * @param type 存储类型
     * @param key 该类型下的key
     * @return
     */
    String getUri(Class<?> type,String key);
    /**
     * 获取加密状态下的uri
     * @param type 存储类型
     * @param key 该类型下的key
     * @return
     */
    String getUriForSecret(Class<?> type,String key);

    /**
     * 删除指定uri的数据
     * @param uri
     */
    void deleteValue(String uri);

    /**
     *
     * @param uri
     * @param value
     */
    void setInt(String uri,int value);
    /**
     *
     * @param uri
     * @return
     */
    Integer getInt(String uri);


    /**
     *
     * @param uri
     * @param value
     */
    void setLong(String uri,long value);
    /**
     *
     * @param uri
     * @return
     */
    Long getLong(String uri);

    /**
     *
     * @param uri
     * @param value
     */
    void setFloat(String uri,float value);
    /**
     *
     * @param uri
     * @return
     */
    Float getFloat(String uri);


    /**
     *
     * @param uri
     * @param value
     */
    void setDouble(String uri,double value);
    /**
     *
     * @param uri
     * @return
     */
    Double getDouble(String uri);

    /**
     *
     * @param uri
     * @param value
     */
    void setChar(String uri,char value);
    /**
     *
     * @param uri
     * @return
     */
    Character getChar(String uri);

    /**
     *
     * @param uri
     * @param value
     */
    void setBoolean(String uri,boolean value);
    /**
     *
     * @param uri
     * @return
     */
    Boolean getBoolean(String uri);

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
