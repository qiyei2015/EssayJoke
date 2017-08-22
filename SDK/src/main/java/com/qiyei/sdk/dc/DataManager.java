package com.qiyei.sdk.dc;


/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/20.
 * Version: 1.0
 * Description: 数据中心的Manager，对外提供数据的统一访问
 */
public class DataManager {

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

    }

    /**
     * 内部类方式获取单例
     * @return
     */
    public static DataManager getInstance(){
        return SingleHolder.sInstance;
    }


    public void addDataObserver(){

    }


}
