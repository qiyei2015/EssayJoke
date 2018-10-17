package com.qiyei.mall.ordermanager.service.impl

import com.qiyei.mall.ordermanager.data.repository.OrderManagerRepository
import com.qiyei.mall.ordermanager.service.IOrderManagerService
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/17.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class OrderManagerServiceImpl @Inject constructor():IOrderManagerService {

    @Inject
    lateinit var mOrderManagerRepository: OrderManagerRepository


}