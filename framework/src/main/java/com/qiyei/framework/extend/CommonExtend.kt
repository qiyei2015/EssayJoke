package com.qiyei.framework.extend


import com.qiyei.framework.rx.BaseObserver
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 用于kotlin 公共的扩展方法
 */

/**
 * 扩展Observable执行,部分公共逻辑进行封装
 */
fun <T> Observable<T>.execute(observer:BaseObserver<T>,lifecycleProvider:LifecycleProvider<*>){
    this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(lifecycleProvider.bindToLifecycle())
            .subscribe(observer)
}


