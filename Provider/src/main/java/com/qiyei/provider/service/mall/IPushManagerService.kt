package com.qiyei.provider.service.mall

import com.qiyei.provider.service.IBaseProvider

/**
 * @author Created by qiyei2015 on 2018/10/27.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IPushManagerService: IBaseProvider {

    fun getPushId():String
}