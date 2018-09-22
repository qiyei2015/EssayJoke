package com.qiyei.framework.mvp.presenter

import com.qiyei.framework.mvp.view.IBaseView

/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: MVP 中Presenter基类,持有View的引用
 */
open class BasePresenter<T:IBaseView> {

    lateinit var mView:T

}