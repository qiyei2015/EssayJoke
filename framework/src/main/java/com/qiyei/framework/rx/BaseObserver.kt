package com.qiyei.framework.rx

import android.util.Log
import com.qiyei.framework.mvp.view.IBaseView
import com.qiyei.sdk.log.LogManager
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.getStackTraceString

/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: Rx的基类订阅器，继承Observer实现
 */
open class BaseObserver<T>(private val baseView:IBaseView) : Observer<T>{

    companion object {
        const val TAG = "BaseObserver"
    }

    override fun onComplete() {
        Log.i(TAG,"onComplete")
        baseView.hideLoading()
    }

    override fun onSubscribe(d: Disposable) {
        //显示加载对话框
        Log.i(TAG,"onSubscribe")
        baseView.showLoading()
    }

    override fun onNext(t: T) {

    }

    override fun onError(e: Throwable) {
        baseView.hideLoading()
        baseView.onError(e.toString())
    }

}