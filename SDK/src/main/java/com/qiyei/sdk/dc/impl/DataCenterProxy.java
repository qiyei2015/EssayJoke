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
     * 存储String类型数据
     * @param uri
     * @param value
     */
    public void setString(String uri,String value){
        DataCenter.getInstance().setValue(uri,value);
    }

    /**
     * 存储String类型数据
     * @param uri
     */
    public String getString(String uri){
        return DataCenter.getInstance().getValue(uri);
    }

}
