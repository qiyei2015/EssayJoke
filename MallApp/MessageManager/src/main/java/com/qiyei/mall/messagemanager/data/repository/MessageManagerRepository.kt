package com.qiyei.mall.messagemanager.data.repository


import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.framework.net.RetrofitFactory
import com.qiyei.mall.messagemanager.data.api.IMessageManagerApi
import com.qiyei.mall.messagemanager.data.bean.Message
import io.reactivex.Observable
import javax.inject.Inject




/**
 * @author Created by qiyei2015 on 2018/10/27.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class MessageManagerRepository @Inject constructor() {

    /**
     * 获取消息列表
     */
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>> {
        return RetrofitFactory.INSTANCE.create(IMessageManagerApi::class.java).getMessageList()
    }

}
