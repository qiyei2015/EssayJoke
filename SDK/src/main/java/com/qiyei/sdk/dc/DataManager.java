package com.qiyei.sdk.dc;



import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.dc.impl.DataCenterProxy;
import com.qiyei.sdk.dc.impl.IDataCenterObserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/20.
 * Version: 1.0
 * Description: 数据中心的Manager，对外提供数据的统一访问
 */
public class DataManager implements IDataOperator{
    /**
     * 数据观察者，外部使用
     */
    private List<DataObserver> mObservers = new ArrayList<>();
    /**
     * DataCenter的代理
     */
    private DataCenterProxy mProxy;

    /**
     * 内部类方式构造单例
     */
    private static class SingleHolder{
        static final DataManager sInstance = new DataManager();
    }

    /**
     * 构造方法私有化
     */
    private DataManager(){
        mProxy = new DataCenterProxy(RuntimeEnv.appContext,new IDataCenterObserver() {

            @Override
            public void onDataChanged(Set<String> urlSet) {
                Set<String> updateUris = new HashSet<String>();

                for (DataObserver observer : mObservers){
                    //只更新关心的
                    for (String uri : urlSet){
                        if (observer.getUri().contains(uri)){
                            updateUris.add(uri);
                        }
                    }
                    //updateUris有内容才更新
                    if (updateUris.size() > 0){
                        observer.onDataChanged(updateUris);
                    }
                    //清除，便于下一次循环使用
                    updateUris.clear();
                }
            }

            @Override
            public void onDataDeleted(Set<String> urlSet) {
                for (DataObserver observer : mObservers){
                    observer.onDataDeleted(urlSet);
                }
            }
        });
    }

    /**
     * 内部类方式获取单例
     * @return
     */
    public static DataManager getInstance(){
        return SingleHolder.sInstance;
    }

    /**
     * 注册数据观察者
     * @param uriSet 感兴趣的uri
     * @param observer
     */
    public void registerDataObserver(Set<String> uriSet,DataObserver observer){
        if (!mObservers.contains(observer)){
            //添加感兴趣的uri
            observer.addUri(uriSet);
            //添加到mObservers中
            mObservers.add(observer);
        }
    }

    /**
     * 取消注册
     * @param observer
     */
    public void unregisterDataObserver(DataObserver observer){
        if (mObservers.contains(observer)){
            mObservers.remove(observer);
        }
    }

    /**
     * 获取普通的uri
     * @param type
     * @param key
     * @return
     */
    @Override
    public String getUri(String type,String key){
        return DCConstant.URI + "/" + type + "/" + key;
    }

    /**
     * 获取加密的uri
     * @param type
     * @param key
     * @return
     */
    @Override
    public String getUriForSecret(String type,String key){
        return DCConstant.URI_SECRET + "/" + type + "/" + key;
    }

    @Override
    public void setInt(String uri, int value) {
        mProxy.setInt(uri,value);
    }

    @Override
    public int getInt(String uri, int defValue) {
        Integer value = mProxy.getInt(uri);
        return value == null ? defValue : value;
    }

    @Override
    public void setLong(String uri, long value) {
        mProxy.setLong(uri,value);
    }

    @Override
    public long getLong(String uri, long defValue) {
        Long value = mProxy.getLong(uri);
        return value == null ? defValue : value;
    }

    @Override
    public void setFloat(String uri, float value) {
        mProxy.setFloat(uri,value);
    }

    @Override
    public float getFloat(String uri, float defValue) {
        Float value = mProxy.getFloat(uri);
        return value == null ? defValue : value;
    }

    @Override
    public void setDouble(String uri, double value) {
        mProxy.setDouble(uri,value);
    }

    @Override
    public double getDouble(String uri, double defValue) {
        Double value = mProxy.getDouble(uri);
        return value == null ? defValue : value;
    }

    @Override
    public void setChar(String uri, char value) {
        mProxy.setChar(uri,value);
    }

    @Override
    public char getChar(String uri, char defValue) {
        Character value = mProxy.getChar(uri);
        return value == null ? defValue : value;
    }

    @Override
    public void setBoolean(String uri, boolean value) {
        mProxy.setBoolean(uri,value);
    }

    @Override
    public boolean getBoolean(String uri, boolean defValue) {
        Boolean value = mProxy.getBoolean(uri);
        return value == null ? defValue : value;
    }

    /**
     * 存储String类型数据
     * @param uri
     * @param value
     */
    @Override
    public void setString(String uri,String value){
        mProxy.setString(uri,value);
    }

    /**
     * 存储String类型数据
     * @param uri
     */
    @Override
    public String getString(String uri,String defValue){
        String value = mProxy.getString(uri);
        return value == null ? defValue : value;
    }

}
