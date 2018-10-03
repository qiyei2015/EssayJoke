package com.qiyei.framework.rx

import com.qiyei.framework.data.protocol.BaseResp
import io.reactivex.functions.Function

/**
 * @author Created by qiyei2015 on 2018/10/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface BaseFunction<T,R> :Function<BaseResp<T>,R>