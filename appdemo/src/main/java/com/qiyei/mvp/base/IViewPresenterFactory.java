package com.qiyei.mvp.base;

/**
 * @author Created by qiyei2015 on 2018/5/1.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 创建View与Presenter的抽象工厂
 */
public interface IViewPresenterFactory<V extends IView,P extends IPresenter<V>> {

    /**
     * 创建View
     * @return
     */
    V createView();

    /**
     * 创建Presenter
     * @return
     */
    P createPresenter();

}
