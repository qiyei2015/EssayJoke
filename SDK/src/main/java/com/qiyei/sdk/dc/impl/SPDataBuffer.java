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
    private String suffix = "_sharePref";
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
    public void setValue(String uri, String value) {
        mEditor.putString(uri,value);
        if (value.length() > VALUE_SIZE){
            mEditor.commit();
        }else {
            mEditor.apply();
        }
    }

    @Override
    public String getValue(String uri) {
        return mSharedPreferences.getString(uri,null);
    }

    @Override
    public void deleteValue(String uri) {
        mEditor.remove(uri);
        mEditor.commit();
    }

}
