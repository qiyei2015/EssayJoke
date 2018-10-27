package com.qiyei.mall.usermanager.data.api

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.mall.usermanager.data.bean.UserInfo
import com.qiyei.mall.usermanager.data.protocol.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * @author Created by qiyei2015 on 2018/9/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 网络请求API
 */
interface IUserManagerApi {
    /**
     * 用户注册
     */
    @POST("userCenter/register")
    fun register(@Body req: RegisterReq):Observable<BaseResp<String>>

    /**
     * 用户登录
     */
    @POST("userCenter/login")
    fun login(@Body req: LoginReq):Observable<BaseResp<UserInfo>>

    /**
     * 忘记密码
     */
    @POST("userCenter/forgetPwd")
    fun forgetPassword(@Body req: ForgetPasswordReq):Observable<BaseResp<String>>

    /**
     * 用户登录
     */
    @POST("userCenter/resetPwd")
    fun modifyPassword(@Body req: ModifyPasswordReq):Observable<BaseResp<String>>

    /**
     * 编辑修改用户资料
     */
    @POST("userCenter/editUser")
    fun modifyUserInfo(@Body req:EditUserInfoReq):Observable<BaseResp<UserInfo>>
}