package com.qiyei.sdk.dc.impl;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/22.
 * Version: 1.0
 * Description: 内存数据
 */
public class MemoryDataBuffer implements IDataBuffer {


    private Map<String,String> mDataMap;

    @Override
    public void init() {
        mDataMap = new HashMap<>();
    }

    @Override
    public String getValue(String key) {
        return mDataMap.get(key);
    }

    @Override
    public boolean setValue(String key, String value) {
        return !TextUtils.isEmpty(mDataMap.put(key,value));
    }
}
