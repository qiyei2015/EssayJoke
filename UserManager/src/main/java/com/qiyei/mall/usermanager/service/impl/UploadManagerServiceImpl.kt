package com.qiyei.mall.usermanager.service.impl

import com.qiyei.framework.extend.baseRespConvert
import com.qiyei.mall.usermanager.data.respository.UserUploadRespository
import com.qiyei.mall.usermanager.service.IUploadManagerService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class UploadManagerServiceImpl @Inject constructor():IUploadManagerService {
    @Inject
    lateinit var mUploadRespository:UserUploadRespository

    override fun getUploadToken(): Observable<String> {
        return mUploadRespository.getUploadToken().baseRespConvert()
    }

}