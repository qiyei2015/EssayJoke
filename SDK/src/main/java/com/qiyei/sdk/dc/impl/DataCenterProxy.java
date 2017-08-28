package com.qiyei.sdk.dc.impl;

import android.content.Context;
import android.text.TextUtils;

import com.qiyei.sdk.dc.DCConstant;
import com.qiyei.sdk.log.LogManager;

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
     * 删除指定的uri的数据
     * @param uri
     */
    public void deleteValue(String  uri){
        if (TextUtils.isEmpty(uri)){
            LogManager.w(DCConstant.TAG,"deleteValue(String  uri),the uri is null");
            return;
        }
        mDataCenter.deleteStringValue(uri);
    }

    /**
     * 保存int
     * @param uri
     * @param value
     */
    public void setInt(String uri, int value) {
        setValue(uri,value+"");
    }

    /**
     * 获取int
     * @param uri
     * @return
     */
    public Integer getInt(String uri) {
        if (TextUtils.isEmpty(getValue(uri))){
            return null;
        }
        try {
            return Integer.parseInt(getValue(uri));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存long
     * @param uri
     * @param value
     */
    public void setLong(String uri, long value) {
        setValue(uri,value+"");
    }

    /**
     * 获取long
     * @param uri
     * @return
     */
    public Long getLong(String uri) {
        if (TextUtils.isEmpty(getValue(uri))){
            return null;
        }
        try {
            return Long.parseLong(getValue(uri));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存float
     * @param uri
     * @param value
     */
    public void setFloat(String uri, float value) {
        setValue(uri,value+"");
    }

    /**
     * 获取float
     * @param uri
     * @return
     */
    public Float getFloat(String uri) {
        if (TextUtils.isEmpty(getValue(uri))){
            return null;
        }
        try {
            return Float.parseFloat(getValue(uri));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存double
     * @param uri
     * @param value
     */
    public void setDouble(String uri, double value) {
        setValue(uri,value+"");
    }

    /**
     * 获取double
     * @param uri
     * @return
     */
    public Double getDouble(String uri) {
        if (TextUtils.isEmpty(getValue(uri))){
            return null;
        }
        try {
            return Double.parseDouble(getValue(uri));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存char
     * @param uri
     * @param value
     */
    public void setChar(String uri, char value) {
        setValue(uri,value+"");
    }

    /**
     * 获取char
     * @param uri
     * @return
     */
    public Character getChar(String uri) {
        if (TextUtils.isEmpty(getValue(uri))){
            return null;
        }
        return getValue(uri).toCharArray()[0];
    }

    /**
     * 保存boolean
     * @param uri
     * @param value
     */
    public void setBoolean(String uri, boolean value) {
        setValue(uri,value+"");
    }

    /**
     * 获取boolean
     * @param uri
     * @return
     */
    public Boolean getBoolean(String uri) {
        if (TextUtils.isEmpty(getValue(uri))){
            return null;
        }
        try {
            return Boolean.parseBoolean(getValue(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
     * 将所有数据都保存为String方式
     * @param uri
     * @param value
     */
    private void setValue(String uri,String value){
        if (TextUtils.isEmpty(uri) || TextUtils.isEmpty(value)){
            LogManager.w(DCConstant.TAG,"setValue(String uri,String value),the uri or value is null");
            return;
        }
        mDataCenter.setStringValue(uri,value);
    }

    /**
     * 所有数据都以String类型获取
     * @param uri
     * @return
     */
    private String getValue(String uri){
        if (TextUtils.isEmpty(uri)){
            LogManager.w(DCConstant.TAG,"getValue(String uri),the uri is null");
            return null;
        }
        return mDataCenter.getStringValue(uri);
    }

}
