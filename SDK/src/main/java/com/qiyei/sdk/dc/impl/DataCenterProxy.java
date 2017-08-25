package com.qiyei.sdk.dc.impl;

import android.content.Context;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/24.
 * Version: 1.0
 * Description: DataCenter的代理
 */
public class DataCenterProxy {


    public DataCenterProxy(Context context,IDataCenterObserver observer){
        //注册
        DataCenter.getInstance().registerDataObserver(observer);
    }

    /**
     * 保存int
     * @param uri
     * @param value
     */
    public void setInt(String uri, int value) {
        setValue(uri,value + "");
    }

    /**
     * 获取int
     * @param uri
     * @return
     */
    public Integer getInt(String uri) {
        Integer value = null;
        if (getValue(uri) != null){
            value = Integer .valueOf(getValue(uri));
        }
        return value;
    }

    /**
     * 保存long
     * @param uri
     * @param value
     */
    public void setLong(String uri, long value) {
        setValue(uri,value + "");
    }

    /**
     * 获取long
     * @param uri
     * @return
     */
    public Long getLong(String uri) {
        Long value = null;
        if (getValue(uri) != null){
            value = Long .valueOf(getValue(uri));
        }
        return value;
    }

    /**
     * 保存float
     * @param uri
     * @param value
     */
    public void setFloat(String uri, float value) {
        setValue(uri,value + "");
    }

    /**
     * 获取float
     * @param uri
     * @return
     */
    public Float getFloat(String uri) {
        Float value = null;
        if (getValue(uri) != null){
            value = Float .valueOf(getValue(uri));
        }
        return value;
    }

    /**
     * 保存double
     * @param uri
     * @param value
     */
    public void setDouble(String uri, double value) {
        setValue(uri,value + "");
    }

    /**
     * 获取double
     * @param uri
     * @return
     */
    public Double getDouble(String uri) {
        Double value = null;
        if (getValue(uri) != null){
            value = Double .valueOf(getValue(uri));
        }
        return value;
    }

    /**
     * 保存char
     * @param uri
     * @param value
     */
    public void setChar(String uri, char value) {
        setValue(uri,value + "");
    }

    /**
     * 获取char
     * @param uri
     * @return
     */
    public Character getChar(String uri) {
        char[] value = null;
        if (getValue(uri) != null){
            value = getValue(uri).toCharArray();
        }
        return value[0];
    }

    /**
     * 保存boolean
     * @param uri
     * @param value
     */
    public void setBoolean(String uri, boolean value) {
        setValue(uri,value + "");
    }

    /**
     * 获取boolean
     * @param uri
     * @return
     */
    public Boolean getBoolean(String uri) {
        Boolean value = null;
        if (getValue(uri) != null){
            if ("true".equals(getValue(uri))){
                value = true;
            }else if ("false".equals(getValue(uri))){
                value = false;
            }
        }
        return value;
    }

    /**
     * 存储String类型数据
     * @param uri
     * @param value
     */
    public void setString(String uri,String value){
        setValue(uri,value);
    }

    /**
     * 存储String类型数据
     * @param uri
     */
    public String getString(String uri){
        return getValue(uri);
    }

    /**
     * 保存值
     * @param uri
     * @param value
     */
    private void setValue(String uri,String value){
        DataCenter.getInstance().setValue(uri,value);
    }

    /**
     * 获取值
     * @return
     */
    private String getValue(String uri){
        return DataCenter.getInstance().getValue(uri);
    }

}
