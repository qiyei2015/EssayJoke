package com.qiyei.sdk.dc.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.qiyei.sdk.common.RuntimeEnv;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/22.
 * Version: 1.0
 * Description: SharePreferences 中数据
 */
public class SPDataBuffer implements IDataBuffer {

    /**
     * Context
     */
    private Context mContext;
    /**
     * 后缀
     */
    private String suffix = ".sharePref";
    /**
     * 存储的数据大小
     */
    private static final int VALUE_SIZE = 100;
    /**
     * 保存的sharePrefenerces
     */
    private SharedPreferences mSharedPreferences;
    /**
     * 保存的sharePrefenerces
     */
    private SharedPreferences.Editor mEditor;

    /**
     * 同一个包下可以引用
     * @param context
     */
    SPDataBuffer(Context context){
        mContext = context;
        //名称默认为包名
        mSharedPreferences = mContext.getSharedPreferences(RuntimeEnv.packageName + suffix,Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    @Override
    public void setInt(String uri, int value) {
        mEditor.putInt(uri,value);
        mEditor.commit();
    }

    @Override
    public Integer getInt(String uri) {
        if (mSharedPreferences.contains(uri)){
            //获取最小值
            return mSharedPreferences.getInt(uri,Integer.MIN_VALUE);
        }
        return null;
    }

    @Override
    public void setLong(String uri, long value) {
        mEditor.putLong(uri,value);
        mEditor.commit();
    }

    @Override
    public Long getLong(String uri) {
        if (mSharedPreferences.contains(uri)){
            //获取最小值
            return mSharedPreferences.getLong(uri,Long.MIN_VALUE);
        }
        return null;
    }

    @Override
    public void setFloat(String uri, float value) {
        mEditor.putFloat(uri,value);
        mEditor.commit();
    }

    @Override
    public Float getFloat(String uri) {
        if (mSharedPreferences.contains(uri)){
            //获取最小值
            return mSharedPreferences.getFloat(uri,Float.MIN_VALUE);
        }
        return null;
    }

    @Override
    public void setDouble(String uri, double value) {
        mEditor.putString(uri,String.valueOf(value));
        mEditor.commit();
    }

    @Override
    public Double getDouble(String uri) {
        if (mSharedPreferences.contains(uri)){
            //获取最小值
            if (!TextUtils.isEmpty(mSharedPreferences.getString(uri,null))){
                return Double.parseDouble(mSharedPreferences.getString(uri,null));
            }
        }
       return null;
    }

    @Override
    public void setChar(String uri, char value) {
        mEditor.putString(uri,String.valueOf(value));
        mEditor.commit();
    }

    @Override
    public Character getChar(String uri) {
        if (mSharedPreferences.contains(uri)){
            //获取最小值
            if (!TextUtils.isEmpty(mSharedPreferences.getString(uri,null))){
                return mSharedPreferences.getString(uri,null).toCharArray()[0];
            }
        }
        return null;
    }

    @Override
    public void setBoolean(String uri, boolean value) {
        mEditor.putBoolean(uri,value);
        mEditor.commit();
    }

    @Override
    public Boolean getBoolean(String uri) {
        if (mSharedPreferences.contains(uri)){
            //获取最小值
            return mSharedPreferences.getBoolean(uri,false);
        }
        return null;
    }

    @Override
    public void setString(String uri, String value) {
        mEditor.putString(uri,value);
        if (value.length() > VALUE_SIZE){
            mEditor.commit();
        }else {
            mEditor.apply();
        }
    }

    @Override
    public String getString(String uri) {
        return mSharedPreferences.getString(uri,null);
    }

}
