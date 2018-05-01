package com.qiyei.mvp.base;

/**
 * @author Created by qiyei2015 on 2018/5/1.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 所有Presenter的公共接口
 */
public interface IPresenter<V extends IView> {

    /**
     * 绑定View
     * @param v
     */
    void onBindView(V v);

    /**
     * 解绑View
     */
    void onUnbindView();

    /**
     * 生命周期同步
     */
    void onCreate();

    /**
     * 生命周期同步
     */
    void onDestory();

}
