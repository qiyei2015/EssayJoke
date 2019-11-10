package com.qiyei.sdk.dc;

import java.util.HashSet;
import java.util.Set;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/22.
 * Version: 1.0
 * Description:
 */
public abstract class DataObserver {

    /**
     * 感兴趣的uris
     */
    private Set<String> mUriSet;

    /**
     * 构造方法
     */
    public DataObserver(){
        mUriSet = new HashSet<>();
    }

    /**
     * 数据更新
     * @param uriSet
     */
    public abstract void onDataChanged(Set<String> uriSet);

    /**
     * 数据被删除
     * @param uriSet
     */
    public void onDataDeleted(Set<String> uriSet){

    }

    /**
     * 添加感兴趣的uri,不允许重复添加
     * @param uriSet
     */
    public void addUri(Set<String> uriSet){
        for (String uri : uriSet){
            if (!mUriSet.contains(uri)){
                mUriSet.add(uri);
            }
        }
    }

    /**
     * @return {@link #mUriSet}
     */
    public Set<String> getUri() {
        return mUriSet;
    }

}
