package com.qiyei.mall.usermanager.service.impl

import com.qiyei.mall.usermanager.service.IUserManagerService
import io.reactivex.Observable

/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:用户管理服务实现
 */
class UserManagerServiceImpl:IUserManagerService {

    override fun register(userKey: String, password: String, verifyCode: String): Observable<Boolean> {

        //注册成功
        return Observable.just(true)
    }

}