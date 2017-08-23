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

    private Set<String> mUris;

    /**
     * 感兴趣的uris
     */
    public DataObserver(){
        mUris = new HashSet<>();
    }

    /**
     * 数据更新
     * @param uris
     */
    public abstract void dataUpdate(Set<String> uris);

    /**
     * 数据被删除
     * @param uris
     */
    public void dataDeleted(Set<String> uris){

    }

    /**
     * 添加感兴趣的uri,不允许重复添加
     * @param uris
     */
    public void addUris(Set<String> uris){
        for (String uri : uris){
            if (!mUris.contains(uri)){
                mUris.add(uri);
            }
        }
    }

    /**
     * @return {@link #mUris}
     */
    public Set<String> getUris() {
        return mUris;
    }

}
