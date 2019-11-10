package com.qiyei.sdk.dc.impl;

import java.util.Set;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/23.
 * Version: 1.0
 * Description: 数据中心变化通知客户端
 */
public interface IDataCenterObserver {

    /**
     * 数据更新
     * @param urlSet
     */
    void onDataChanged(Set<String> urlSet);

    /**
     * 数据被删除
     * @param urlSet
     */
    void onDataDeleted(Set<String> urlSet);


}
