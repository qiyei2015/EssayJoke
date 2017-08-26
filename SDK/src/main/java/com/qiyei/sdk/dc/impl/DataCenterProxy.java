package com.qiyei.sdk.dc.impl;

import android.content.Context;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/24.
 * Version: 1.0
 * Description: DataCenter的代理
 */
public class DataCenterProxy {

    /**
     * 数据中心
     */
    private DataCenter mDataCenter;

    public DataCenterProxy(Context context,IDataCenterObserver observer){
        //注册
        mDataCenter = DataCenter.getInstance();
        mDataCenter.registerDataObserver(observer);
    }

    /**
     * 保存int
     * @param uri
     * @param value
     */
    public void setInt(String uri, int value) {
        mDataCenter.setInt(uri,value);
    }

    /**
     * 获取int
     * @param uri
     * @return
     */
    public Integer getInt(String uri) {
        return mDataCenter.getInt(uri);
    }

    /**
     * 保存long
     * @param uri
     * @param value
     */
    public void setLong(String uri, long value) {
        mDataCenter.setLong(uri,value);
    }

    /**
     * 获取long
     * @param uri
     * @return
     */
    public Long getLong(String uri) {
        return mDataCenter.getLong(uri);
    }

    /**
     * 保存float
     * @param uri
     * @param value
     */
    public void setFloat(String uri, float value) {
        mDataCenter.setFloat(uri,value);
    }

    /**
     * 获取float
     * @param uri
     * @return
     */
    public Float getFloat(String uri) {
        return mDataCenter.getFloat(uri);
    }

    /**
     * 保存double
     * @param uri
     * @param value
     */
    public void setDouble(String uri, double value) {
        mDataCenter.setDouble(uri,value);
    }

    /**
     * 获取double
     * @param uri
     * @return
     */
    public Double getDouble(String uri) {
        return mDataCenter.getDouble(uri);
    }

    /**
     * 保存char
     * @param uri
     * @param value
     */
    public void setChar(String uri, char value) {
        mDataCenter.setChar(uri,value);
    }

    /**
     * 获取char
     * @param uri
     * @return
     */
    public Character getChar(String uri) {
        return mDataCenter.getChar(uri);
    }

    /**
     * 保存boolean
     * @param uri
     * @param value
     */
    public void setBoolean(String uri, boolean value) {
        mDataCenter.setBoolean(uri,value);
    }

    /**
     * 获取boolean
     * @param uri
     * @return
     */
    public Boolean getBoolean(String uri) {
        return mDataCenter.getBoolean(uri);
    }

    /**
     * 存储String类型数据
     * @param uri
     * @param value
     */
    public void setString(String uri,String value){
        mDataCenter.setString(uri,value);
    }

    /**
     * 存储String类型数据
     * @param uri
     */
    public String getString(String uri){
        return mDataCenter.getString(uri);
    }

}
