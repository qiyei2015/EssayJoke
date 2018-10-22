package com.qiyei.paymanager.service.impl

import com.qiyei.paymanager.data.repository.PayManagerRepository
import com.qiyei.paymanager.service.IPayManagerService
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


}