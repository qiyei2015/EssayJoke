package com.qiyei.sdk.dc.impl;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/22.
 * Version: 1.0
 * Description: 内存数据
 */
public class MEMDataBuffer implements IDataBuffer {

    /**
     * 保存数据的Map
     */
    private Map<String,Object> mDataMap;

    /**
     * 同一个包下可以引用
     * @param context
     */
    MEMDataBuffer(Context context){
        mDataMap = new HashMap<>();
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    private Object getValue(String key) {
        return mDataMap.get(key);
    }

    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    private boolean setValue(String key, Object value) {
        if (mDataMap.put(key,value) == null){
            return false;
        }else {
            return true;
        }
    }


    @Override
    public void setInt(String uri, int value) {
        setValue(uri,value);
    }

    @Override
    public Integer getInt(String uri) {
        return (Integer) getValue(uri);
    }

    @Override
    public void setLong(String uri, long value) {
        setValue(uri,value);
    }

    @Override
    public Long getLong(String uri) {
        return (Long) getValue(uri);
    }

    @Override
    public void setFloat(String uri, float value) {
        setValue(uri,value);
    }

    @Override
    public Float getFloat(String uri) {
        return (Float) getValue(uri);
    }

    @Override
    public void setDouble(String uri, double value) {
        setValue(uri,value);
    }

    @Override
    public Double getDouble(String uri) {
        return (Double) getValue(uri);
    }

    @Override
    public void setChar(String uri, char value) {
        setValue(uri,value);
    }

    @Override
    public Character getChar(String uri) {
        return (Character) getValue(uri);
    }

    @Override
    public void setBoolean(String uri, boolean value) {
        setValue(uri,value);
    }

    @Override
    public Boolean getBoolean(String uri) {
        return (Boolean) getValue(uri);
    }

    @Override
    public void setString(String uri, String value) {
        setValue(uri,value);
    }

    @Override
    public String getString(String uri) {
        return (String) getValue(uri);
    }

}
