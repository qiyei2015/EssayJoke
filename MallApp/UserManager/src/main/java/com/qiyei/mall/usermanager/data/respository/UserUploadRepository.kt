package com.qiyei.mall.usermanager.data.respository

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.framework.net.RetrofitFactory
import com.qiyei.mall.usermanager.data.api.IUploadManagerApi
import com.qiyei.mall.usermanager.data.api.IUserManagerApi
import com.qiyei.mall.usermanager.data.protocol.RegisterReq
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class UserUploadRepository @Inject constructor() {

    /**
     * 获取七牛云上传凭证
     */
    fun getUploadToken(): Observable<BaseResp<String>> {
        return RetrofitFactory.INSTANCE.create(IUploadManagerApi::class.java).getUploadToken()
    }

}