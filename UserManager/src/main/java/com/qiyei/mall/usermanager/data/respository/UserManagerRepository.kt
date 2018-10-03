package com.qiyei.mall.usermanager.data.respository

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.framework.net.RetrofitFactory
import com.qiyei.mall.usermanager.data.api.IUserManagerApi
import com.qiyei.mall.usermanager.data.bean.UserInfo
import com.qiyei.mall.usermanager.data.protocol.LoginReq
import com.qiyei.mall.usermanager.data.protocol.RegisterReq
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/9/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 用户管理的数据仓库
 */
class UserManagerRepository @Inject constructor() {

    /**
     * 负责发起网络请求或者从缓存中拿去数据
     */
    fun register(userKey:String,password:String,verifyCode:String):Observable<BaseResp<String>>{
        return RetrofitFactory.INSTANCE.create(IUserManagerApi::class.java)
                .register(RegisterReq(userKey,password,verifyCode))
    }

    /**
     * 用户登录
     */
    fun login(userKey:String,password:String,pushId:String):Observable<BaseResp<UserInfo>>{
        return RetrofitFactory.INSTANCE.create(IUserManagerApi::class.java)
                .login(LoginReq(userKey,password,pushId))
    }

}