package com.qiyei.mall.usermanager.data.respository

import android.util.Log
import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.framework.net.RetrofitFactory
import com.qiyei.mall.usermanager.data.api.IUserManagerApi
import com.qiyei.mall.usermanager.data.protocol.RegisterReq
import com.qiyei.sdk.https.HTTP
import io.reactivex.Observable

/**
 * @author Created by qiyei2015 on 2018/9/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class UserManagerRespository {

    /**
     * 负责发起网络请求或者从缓存中拿去数据
     */
    fun register(userKey:String,password:String,verifyCode:String):Observable<BaseResp<String>>{
        return RetrofitFactory.INSTANCE.create(IUserManagerApi::class.java)
                .register(RegisterReq(userKey,password,verifyCode))
    }

}