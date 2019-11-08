package com.qiyei.paymanager.service.impl

import com.qiyei.framework.extend.baseRespConvert
import com.qiyei.framework.extend.baseRespConvertBoolean
import com.qiyei.framework.extend.execute
import com.qiyei.paymanager.data.repository.PayManagerRepository
import com.qiyei.paymanager.service.IPayManagerService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class PayManagerServiceImpl @Inject constructor():IPayManagerService {

    @Inject
    lateinit var mRepository: PayManagerRepository

    override fun getPaySign(orderId: Int, totalPrice: Long): Observable<String> {
        return mRepository.getPaySign(orderId,totalPrice).baseRespConvert()
    }

    override fun payOrder(orderId: Int): Observable<Boolean> {
        return mRepository.payOrder(orderId).baseRespConvertBoolean()
    }
}