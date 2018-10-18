package com.qiyei.mall.ordermanager.service.impl

import com.qiyei.mall.ordermanager.data.repository.AddressManagerRepository
import com.qiyei.mall.ordermanager.service.IAddressManagerService
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class AddressManagerServiceImpl @Inject constructor(): IAddressManagerService {

    @Inject
    lateinit var mAddressRepository: AddressManagerRepository



}