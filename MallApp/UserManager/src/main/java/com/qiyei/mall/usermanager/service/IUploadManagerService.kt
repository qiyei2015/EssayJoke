package com.qiyei.mall.usermanager.service

import io.reactivex.Observable

/**
 * @author Created by qiyei2015 on 2018/10/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IUploadManagerService {

    fun getUploadToken(): Observable<String>

}