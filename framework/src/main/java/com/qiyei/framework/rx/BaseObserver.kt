package com.qiyei.framework.rx

import com.qiyei.framework.mvp.view.IBaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.getStackTraceString

/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: Rx的基类订阅器，继承Observer实现
 */
open class BaseObserver<T>(val baseView:IBaseView) : Observer<T>{
    override fun onComplete() {
        baseView.hideLoading()
    }

    override fun onSubscribe(d: Disposable) {
        //显示加载对话框
        baseView.showLoading()
    }

    override fun onNext(t: T) {

    }

    override fun onError(e: Throwable) {
        baseView.hideLoading()
        baseView.onError(e.getStackTraceString())
    }

}