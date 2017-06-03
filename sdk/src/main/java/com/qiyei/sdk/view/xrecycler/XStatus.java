package com.qiyei.sdk.view.xrecycler;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/25.
 * Version: 1.0
 * Description: 下拉刷新或者上拉加载的状态
 */
public enum XStatus {
    NORMAL,             // 默认状态
    PULL,          // 下拉或者上拉状态
    RELEASE,            // 松开释放状态
    RUNNING          // 正在刷新状态或者正在加载状态
}
