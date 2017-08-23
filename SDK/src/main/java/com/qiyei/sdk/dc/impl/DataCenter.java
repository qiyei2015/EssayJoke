package com.qiyei.sdk.dc.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/20.
 * Version: 1.0
 * Description: 数据中心
 */
public class DataCenter{

    /**
     * 存储观察者数据
     */
    private List<IDataCenterObserver> mCenterObservers = new ArrayList<>();

    private Map<String,IDataBuffer> mBufferMap = new HashMap<>();

    /**
     * 数据buffer
     */
    private IDataBuffer mDataBuffer;

    /**
     * 内部类方式构造单例
     */
    private static class SingleHolder{
        static final DataCenter sInstance = new DataCenter();
    }

    /**
     * 构造方法私有化
     */
    private DataCenter(){
        IDataBuffer memoryBuffer = new MemoryDataBuffer();
        memoryBuffer.init();
        mBufferMap.put("memory",memoryBuffer);
    }

    /**
     * 内部类方式获取单例
     * @return
     */
    public static DataCenter getInstance(){
        return DataCenter.SingleHolder.sInstance;
    }


    /**
     * 注册DataCenter数据观察者
     * @param observer
     */
    public void registerDataObserver(IDataCenterObserver observer){
        if (!mCenterObservers.contains(observer)){
            mCenterObservers.add(observer);
        }
    }

    /**
     * 取消观察者注册
     * @param observer
     */
    public void unregisterDataObserver(IDataCenterObserver observer){
        if (mCenterObservers.contains(observer)){
            mCenterObservers.remove(observer);
        }
    }

    /**
     * 根据uri返回相应的DataBuffer
     * @param uri
     * @return
     */
    private IDataBuffer getDataBuffer(String uri){
        return mBufferMap.get("memory");
    }

    /**
     * 存储String类型数据
     * @param uri
     * @param value
     */
    public void setValue(String uri,String value){
        mDataBuffer = getDataBuffer(uri);

        mDataBuffer.setValue(uri,value);
        //更新数据
        Set<String> uris = new HashSet<>();
        uris.add(uri);
        for (IDataCenterObserver observer : mCenterObservers){
            observer.dataUpdate(uris);
        }

    }

    /**
     * 存储String类型数据
     * @param uri
     */
    public String getValue(String uri){
        mDataBuffer = getDataBuffer(uri);
        return mDataBuffer.getValue(uri);
    }



}
