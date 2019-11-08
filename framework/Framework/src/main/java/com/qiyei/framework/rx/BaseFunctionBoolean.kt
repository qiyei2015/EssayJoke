package com.qiyei.framework.rx


import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.framework.net.ResultCode
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function

/**
 * @author Created by qiyei2015 on 2018/9/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
open class BaseFunctionBoolean<T>:Function<BaseResp<T>, ObservableSource<Boolean>> {
    override fun apply(t: BaseResp<T>): ObservableSource<Boolean> {
        if (t.status != ResultCode.SUCCESS){
            return Observable.error(BaseException(t.status,t.message))
        }
        return Observable.just(true)
    }
}