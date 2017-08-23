package com.qiyei.sdk.dc;



import com.qiyei.sdk.dc.impl.DataCenter;
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
public class DataManager{

    /**
     * 数据观察者，外部使用
     */
    private List<DataObserver> mObservers = new ArrayList<>();

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

        //注册数据中心
        DataCenter.getInstance().registerDataObserver(new IDataCenterObserver() {

            @Override
            public void dataUpdate(Set<String> uris) {
                Set<String> updateUris = new HashSet<String>();

                for (DataObserver observer : mObservers){
                    //只更新关心的
                    for (String uri : uris){
                        if (observer.getUris().contains(uri)){
                            updateUris.add(uri);
                        }
                    }
                    if (updateUris.size() > 0){
                        observer.dataUpdate(updateUris);
                    }
                    updateUris.clear();

                    //observer.dataUpdate(uris);
                }
            }

            @Override
            public void dataDeleted(Set<String> uris) {
                for (DataObserver observer : mObservers){
                    observer.dataDeleted(uris);
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
     * @param uris 感兴趣的uris
     * @param observer
     */
    public void registerDataObserver(Set<String> uris,DataObserver observer){
        if (!mObservers.contains(observer)){
            //添加感兴趣的uri
            observer.addUris(uris);
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
