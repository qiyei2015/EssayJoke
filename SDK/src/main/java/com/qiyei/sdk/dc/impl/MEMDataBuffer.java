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
    private Map<String,String> mDataMap;

    /**
     * 同一个包下可以引用
     * @param context
     */
    MEMDataBuffer(Context context){
        mDataMap = new HashMap<>();
    }

    @Override
    public void setValue(String uri, String value) {
        mDataMap.put(uri,value);
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    @Override
    public String getValue(String key) {
        return mDataMap.get(key);
    }

    @Override
    public void deleteValue(String uri) {
        mDataMap.remove(uri);
    }

}
