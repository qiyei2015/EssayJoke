package com.qiyei.mall.usermanager.data.api

import com.qiyei.framework.data.protocol.BaseResp
import io.reactivex.Observable
import retrofit2.http.POST

/**
 * @author Created by qiyei2015 on 2018/10/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IUploadManagerApi {

    /**
     *获取七牛云上传凭证
     */
    @POST("common/getUploadToken")
    fun getUploadToken(): Observable<BaseResp<String>>
}