package com.qiyei.mall.usermanager.service

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.mall.usermanager.data.bean.UserInfo
import io.reactivex.Observable

/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 用户管理模块对外提供的服务接口
 */
interface IUserManagerService {
    /**
     * 用户注册
     * @param userKey 用户标志，可以是手机，邮箱，或者用户名
     * @param password 用户密码
     * @param verifyCode 验证码
     */
    fun register(userKey:String,password:String,verifyCode:String):Observable<Boolean>

    /**
     * 用户登录
     * @param userKey 用户标志，可以是手机，邮箱，或者用户名
     * @param password 用户密码
     * @param pushId pushId
     */
    fun login(userKey:String,password:String,pushId:String):Observable<UserInfo>


    /**
     * 忘记密码
     * @param userKey 用户标志，可以是手机，邮箱，或者用户名
     * @param verifyCode 验证码
     */
    fun forgetPassword(userKey:String,verifyCode: String):Observable<Boolean>

    /**
     * 修改密码
     * @param userKey 用户标志，可以是手机，邮箱，或者用户名
     * @param password 用户密码
     */
    fun modifyPassword(userKey:String,password:String):Observable<Boolean>

    /**
     * 修改密码
     * @param userIcon 用户头像
     * @param nickName 昵称
     * @param userGender 性别
     * @param userSign 签名
     */
    fun modifyUserInfo(userIcon:String,nickName:String,userGender:String,userSign:String): Observable<UserInfo>

}