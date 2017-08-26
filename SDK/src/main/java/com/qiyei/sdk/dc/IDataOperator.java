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
    String getUri(String type,String key);
    /**
     * 获取加密状态下的uri
     * @param type 存储类型
     * @param key 该类型下的key
     * @return
     */
    String getUriForSecret(String type,String key);

    /**
     *
     * @param uri
     * @param value
     */
    void setInt(String uri,int value);
    /**
     *
     * @param uri
     * @param defValue
     * @return
     */
    int getInt(String uri,int defValue);


    /**
     *
     * @param uri
     * @param value
     */
    void setLong(String uri,long value);
    /**
     *
     * @param uri
     * @param defValue
     * @return
     */
    long getLong(String uri,long defValue);

    /**
     *
     * @param uri
     * @param value
     */
    void setFloat(String uri,float value);
    /**
     *
     * @param uri
     * @param defValue
     * @return
     */
    float getFloat(String uri,float defValue);


    /**
     *
     * @param uri
     * @param value
     */
    void setDouble(String uri,double value);
    /**
     *
     * @param uri
     * @param defValue
     * @return
     */
    double getDouble(String uri,double defValue);

    /**
     *
     * @param uri
     * @param value
     */
    void setChar(String uri,char value);
    /**
     *
     * @param uri
     * @param defValue
     * @return
     */
    char getChar(String uri,char defValue);

    /**
     *
     * @param uri
     * @param value
     */
    void setBoolean(String uri,boolean value);
    /**
     *
     * @param uri
     * @param defValue
     * @return
     */
    boolean getBoolean(String uri,boolean defValue);

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
    String getString(String uri,String defValue);

}
