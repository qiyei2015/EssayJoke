package com.qiyei.sdk.dc;

import com.qiyei.sdk.dc.impl.DC;
import com.qiyei.sdk.dc.impl.DataManagerProxy;
import com.qiyei.sdk.log.LogManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/20.
 * Version: 1.0
 * Description: 数据中心的Manager，对外提供数据的统一访问
 */
public class DataManager{

    /**
     * DataCenter的代理
     */
    private DataManagerProxy mProxy;

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
        mProxy = new DataManagerProxy();
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
     * @param uriSet 感兴趣的key
     * @param observer
     */
    public void registerDataObserver(Set<String> uriSet,DataObserver observer){
        mProxy.registerDataObserver(uriSet,observer);
    }

    /**
     * 注册数据观察者
     * @param uriSet 感兴趣的uri
     * @param observer
     */
    public void registerSecretDataObserver(Set<String> uriSet,DataObserver observer){
        mProxy.registerDataObserver(uriSet,observer);
    }

    /**
     * 取消注册
     * @param observer
     */
    public void unregisterDataObserver(DataObserver observer){
        mProxy.unregisterDataObserver(observer);
    }

    /**
     * 取消注册
     * @param observer
     */
    public void unregisterSecretDataObserver(DataObserver observer){
        mProxy.unregisterDataObserver(observer);
    }

    /**
     * 获取普通的uri
     * @param type
     * @param key
     * @return
     */
    public String getUri(Class<?> type,String key){
        return mProxy.getUri(type,key);
    }

    /**
     * 获取加密的uri
     * @param type
     * @param key
     * @return
     */
    public String getUriForSecret(Class<?> type,String key){
        return mProxy.getUriForSecret(type,key);
    }

    /**
     * 删除指定的uri的数据
     * @param uri
     */
    public void deleteValue(String uri) {
        mProxy.deleteValue(uri);
    }

    /**
     * 设置整形数据
     * @param uri
     * @param value
     */
    public void setInt(String uri, int value) {
        mProxy.setInt(uri,value);
    }

    /**
     * 读取整形数据
     * @param uri
     * @param defValue
     * @return
     */
    public int getInt(String uri, int defValue) {
        Integer value = mProxy.getInt(uri);
        return value == null ? defValue : value;
    }

    /**
     * 设置Long新数据
     * @param uri
     * @param value
     */
    public void setLong(String uri, long value) {
        mProxy.setLong(uri,value);
    }

    /**
     * 读取Long数据
     * @param uri
     * @param defValue
     * @return
     */
    public long getLong(String uri, long defValue) {
        Long value = mProxy.getLong(uri);
        return value == null ? defValue : value;
    }

    /**
     * 设置Float数据
     * @param uri
     * @param value
     */
    public void setFloat(String uri, float value) {
        mProxy.setFloat(uri,value);
    }

    /**
     * 读取Float数据
     * @param uri
     * @param defValue
     * @return
     */
    public float getFloat(String uri, float defValue) {
        Float value = mProxy.getFloat(uri);
        return value == null ? defValue : value;
    }

    /**
     * 设置Double数据
     * @param uri
     * @param value
     */
    public void setDouble(String uri, double value) {
        mProxy.setDouble(uri,value);
    }

    /**
     * 读取Double数据
     * @param uri
     * @param defValue
     * @return
     */
    public double getDouble(String uri, double defValue) {
        Double value = mProxy.getDouble(uri);
        return value == null ? defValue : value;
    }

    /**
     * 设置Char数据
     * @param uri
     * @param value
     */
    public void setChar(String uri, char value) {
        mProxy.setChar(uri,value);
    }

    /**
     * 读取Char数据
     * @param uri
     * @param defValue
     * @return
     */
    public char getChar(String uri, char defValue) {
        Character value = mProxy.getChar(uri);
        return value == null ? defValue : value;
    }

    /**
     * 设置Boolean变量
     * @param uri
     * @param value
     */
    public void setBoolean(String uri, boolean value) {
        mProxy.setBoolean(uri,value);
    }

    /**
     * 读取Boolean类型
     * @param uri
     * @param defValue
     * @return
     */
    public boolean getBoolean(String uri, boolean defValue) {
        Boolean value = mProxy.getBoolean(uri);
        return value == null ? defValue : value;
    }

    /**
     * 存储String类型数据
     * @param uri
     * @param value
     */
    public void setString(String uri,String value){
        mProxy.setString(uri,value);
    }

    /**
     * 存储String类型数据
     * @param uri
     */
    public String getString(String uri,String defValue){
        String value = mProxy.getString(uri);
        return value == null ? defValue : value;
    }

    /**
     * 存储String类型数据
     * @param type
     * @param key
     * @param value
     */
    public void setString(Class<?> type,String key,String value){
        String uri  = getUri(type,key);
        LogManager.d(DC.TAG,"setString uri:" + uri);
        mProxy.setString(uri,value);
    }

    /**
     * 存储String类型数据
     * @param type
     * @param key
     */
    public String getString(Class<?> type,String key,String defValue){
        String uri  = getUri(type,key);
        String value = mProxy.getString(uri);
        LogManager.d(DC.TAG,"getString uri:" + uri + " value:" + value);
        return value == null ? defValue : value;
    }

    /**
     * 将key转换为可识别的uri
     * @param keys
     * @param isSecret 是否使用加密
     * @return
     */
//    private Set<String> convertKeysToUris(Set<String> keys,boolean isSecret){
//        Set<String> uriSet = new HashSet<>();
//        if (isSecret){
//            for (String key : keys){
//                uriSet.add(getUriForSecret())
//            }
//        }
//    }

}
