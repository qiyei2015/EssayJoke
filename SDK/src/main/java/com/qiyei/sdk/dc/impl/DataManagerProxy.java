package com.qiyei.sdk.dc.impl;


import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.dc.DataObserver;
import com.qiyei.sdk.dc.IDataOperator;
import com.qiyei.sdk.log.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.qiyei.sdk.dc.impl.DC.DEFAULT_DATA;
import static com.qiyei.sdk.dc.impl.DC.TAG;

/**
 * @author Created by qiyei2015 on 2018/5/11.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class DataManagerProxy implements IDataOperator {

    /**
     * Kotlin中伴生对象标志
     */
    private static final String KOTLIN_COMPANION_FLAG = "$Companion";

    private DataCenterProxy mProxy;
    /**
     * 数据观察者，外部使用
     */
    private List<DataObserver> mObservers = new ArrayList<>();
    /**
     * 数据观察者，外部使用
     */
    private List<DataObserver> mSecretObservers = new ArrayList<>();

    public DataManagerProxy() {
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

                for (DataObserver observer : mSecretObservers){
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
                for (DataObserver observer : mSecretObservers){
                    observer.onDataDeleted(urlSet);
                }
            }
        });
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
     * 注册数据观察者
     * @param uriSet 感兴趣的uri
     * @param observer
     */
    public void registerSecretDataObserver(Set<String> uriSet,DataObserver observer){
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
     * 取消注册
     * @param observer
     */
    public void unregisterSecretDataObserver(DataObserver observer){
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
    public String getUri(Class<?> type,String key){
        type = adaptForKotlin(type);
        if (type == null || TextUtils.isEmpty(key)){
            LogManager.e(DC.TAG,"getUri(Class<?> type,String key) params has null");
            return null;
        }
        if (type.isInterface()){
            LogManager.e(DC.TAG,"" + type + " is interface");
            return null;
        }
        String realType = getRealTypeByType(type);
        boolean isExist = checkField(type, key);
        if (!isExist){
            LogManager.e(DC.TAG,"checkField(type, key) fail:"  + " key:" + key + " not exist in " + type + " ,realType:" + realType);
            return null;
        }

        return DC.URI + "/" + realType + "/" + key;
    }

    /**
     * 获取加密的uri
     * @param type
     * @param key
     * @return
     */
    @Override
    public String getUriForSecret(Class<?> type,String key){
        type = adaptForKotlin(type);
        if (type == null || TextUtils.isEmpty(key)){
            LogManager.e(DC.TAG,"getUriForSecret(Class<?> type,String key) params has null");
            return null;
        }
        if (type.isInterface()){
            LogManager.e(DC.TAG,"" + type + " is interface");
            return null;
        }
        String realType = getRealTypeByType(type);
        boolean isExist = checkField(type, key);
        if (!isExist){
            LogManager.e(DC.TAG,"checkField(type, key) fail:"  + " key:" + key + " not exist in " + type + " ,realType:" + realType);
            return null;
        }
        return DC.URI_SECRET + "/" + realType + "/" + key;
    }

    /**
     * 删除指定的uri的数据
     * @param uri
     */
    @Override
    public void deleteValue(String uri) {
        mProxy.deleteValue(uri);
    }

    @Override
    public void setInt(String uri, int value) {
        mProxy.setInt(uri,value);
    }

    @Override
    public Integer getInt(String uri) {
        Integer value = mProxy.getInt(uri);
        return value;
    }

    @Override
    public void setLong(String uri, long value) {
        mProxy.setLong(uri,value);
    }

    @Override
    public Long getLong(String uri) {
        Long value = mProxy.getLong(uri);
        return value;
    }

    @Override
    public void setFloat(String uri, float value) {
        mProxy.setFloat(uri,value);
    }

    @Override
    public Float getFloat(String uri) {
        Float value = mProxy.getFloat(uri);
        return value;
    }

    @Override
    public void setDouble(String uri, double value) {
        mProxy.setDouble(uri,value);
    }

    @Override
    public Double getDouble(String uri) {
        Double value = mProxy.getDouble(uri);
        return value;
    }

    @Override
    public void setChar(String uri, char value) {
        mProxy.setChar(uri,value);
    }

    @Override
    public Character getChar(String uri) {
        Character value = mProxy.getChar(uri);
        return value;
    }

    @Override
    public void setBoolean(String uri, boolean value) {
        mProxy.setBoolean(uri,value);
    }

    @Override
    public Boolean getBoolean(String uri) {
        Boolean value = mProxy.getBoolean(uri);
        return value;
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
    public String getString(String uri){
        String value = mProxy.getString(uri);
        return value;
    }

    /**
     * 递归调用获取其所有的父类,除去Object类
     * @param type
     * @param list
     */
    private void getSuperClasses(Class<?> type, List<Class<?>> list) {
        Class<?> superClazz = type.getSuperclass();
        if (superClazz != null && Object.class != superClazz){
            list.add(superClazz);
            getSuperClasses(superClazz,list);
        }
    }

    /**
     * 根据Class类型获取对应的type类型
     * @param type
     * @return
     */
    @NonNull
    private String getRealTypeByType(Class<?> type) {
        String realType;List<Class<?>> classList = new ArrayList<>();
        getSuperClasses(type,classList);
        if (classList.size() == 0){
            classList.add(type);
        }
        Class supperClazz = classList.get(classList.size() - 1);
        //遍历找到其DCConstant中的父类
        if (DC.StoreData.class == supperClazz){
            realType = DC.STORE_DATA;
        }else if (DC.UserData.class == supperClazz){
            realType = DC.USER_DATA;
        }else if (DC.MemoryData.class == supperClazz){
            realType = DC.MEM_DATA;
        }else {
            realType = DEFAULT_DATA;
        }
        return realType;
    }

    /**
     * 检查是否存在Filed
     * @param type
     * @param key
     */
    private boolean checkField(Class<?> type, String key) {
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields){
            //确保修饰符是public static final String 类型的
            if (field.getType() == String.class && Modifier.isPublic(field.getModifiers())
                    && Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())){
                try {
                    String s = (String) field.get(null);
                    if (key.equals(s)){
                        return true;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 为kotlin做适配，kotlin中伴生对象编译出来后传递过来的是xxx$Companion形式，需要去除$Companion
     * @param type
     * @return
     */
    private Class<?> adaptForKotlin(Class<?> type){
        if (type.getName().contains(KOTLIN_COMPANION_FLAG)){
            LogManager.i(TAG,"type:"+ type.getName());
            return type.getDeclaringClass();
        }
        return type;
    }
}
