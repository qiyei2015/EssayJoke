package com.qiyei.mall.usermanager.service.impl


import android.util.Log
import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.framework.net.ResultCode
import com.qiyei.framework.rx.BaseException
import com.qiyei.mall.usermanager.data.respository.UserManagerRespository
import com.qiyei.mall.usermanager.service.IUserManagerService
import com.qiyei.sdk.https.HTTP
import com.qiyei.sdk.log.LogManager
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function


/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:用户管理服务实现
 */
class UserManagerServiceImpl:IUserManagerService {

    override fun register(userKey: String, password: String, verifyCode: String): Observable<Boolean> {
        val respository = UserManagerRespository()
        return respository.register(userKey,password,verifyCode)
                .flatMap(object : Function<BaseResp<String>,ObservableSource<Boolean>>{
                    override fun apply(t: BaseResp<String>): ObservableSource<Boolean> {
                        if (t.status != ResultCode.SUCCESS){
                            return Observable.error(BaseException(t.status,t.message))
                        }
                        return Observable.just(true)
                    }
                })
    }

}