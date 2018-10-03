package com.qiyei.mall.usermanager.service.impl


import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.framework.net.ResultCode
import com.qiyei.framework.rx.BaseException
import com.qiyei.framework.rx.BaseFunction
import com.qiyei.mall.usermanager.data.respository.UserManagerRepository
import com.qiyei.mall.usermanager.service.IUserManagerService
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import javax.inject.Inject


/**
 * @author Created by qiyei2015 on 2018/9/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:用户管理服务实现
 */
class UserManagerServiceImpl @Inject constructor():IUserManagerService {

    @Inject
    lateinit var mRepository:UserManagerRepository

    override fun register(userKey: String, password: String, verifyCode: String): Observable<Boolean> {
        //将Observer<String>转换成Observer<Boolean>
        return mRepository.register(userKey,password,verifyCode)
                .flatMap(object : Function<BaseResp<String>,ObservableSource<Boolean>>{
                    override fun apply(t: BaseResp<String>): ObservableSource<Boolean> {
                        if (t.status != ResultCode.SUCCESS){
                            return Observable.error(BaseException(t.status,t.message))
                        }
                        return Observable.just(true)
                    }
                })
//                .flatMap(object :BaseFunction<BaseResp<String>,Observable<Boolean>>{
//                    override fun apply(t: BaseResp<BaseResp<String>>): Observable<Boolean> {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                    }
//                })

    }

}