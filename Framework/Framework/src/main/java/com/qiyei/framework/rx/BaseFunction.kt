package com.qiyei.framework.rx

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.framework.net.ResultCode
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * @author Created by qiyei2015 on 2018/10/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class BaseFunction<T> :Function<BaseResp<T>,Observable<T>>{
    override fun apply(t: BaseResp<T>): Observable<T> {
        if (t.status == ResultCode.SUCCESS){
            return Observable.just(t.data)
        }else {
            return Observable.error(BaseException(t.status,t.message))
        }
    }

}