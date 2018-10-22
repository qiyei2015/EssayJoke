package com.qiyei.paymanager.data.repository

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.framework.net.RetrofitFactory
import com.qiyei.paymanager.data.api.IPayApi
import com.qiyei.paymanager.data.protocol.PayOrderReq
import com.qiyei.paymanager.data.protocol.PaySignReq
import io.reactivex.Observable
import javax.inject.Inject


/**
 * @author Created by qiyei2015 on 2018/10/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class PayManagerRepository @Inject constructor() {

    /**
     *获取支付宝支付签名
     */
    fun getPaySign(req: PaySignReq): Observable<BaseResp<String>> {
        return RetrofitFactory.INSTANCE.create(IPayApi::class.java).getPaySign(req)
    }

    /**
     *刷新订单状态，已支付
     */
    fun payOrder(req: PayOrderReq): Observable<BaseResp<String>>{
        return RetrofitFactory.INSTANCE.create(IPayApi::class.java).payOrder(req)
    }
}