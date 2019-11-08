package com.qiyei.provider.service

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @author Created by qiyei2015 on 2018/10/26.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IBaseProvider : IProvider {

    /**
     * 调试TAG
     */
    fun getTAG():String

}