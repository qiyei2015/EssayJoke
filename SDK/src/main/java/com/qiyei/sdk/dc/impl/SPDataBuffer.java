package com.qiyei.sdk.dc.impl;

import android.content.Context;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/22.
 * Version: 1.0
 * Description: SharePreferences 中数据
 */
public class SPDataBuffer implements IDataBuffer {

    /**
     * 同一个包下可以引用
     * @param context
     */
    SPDataBuffer(Context context){
        init();
    }


    @Override
    public void init() {

    }

    @Override
    public String getValue(String key) {
        return null;
    }

    @Override
    public boolean setValue(String key, String value) {
        return false;
    }
}
