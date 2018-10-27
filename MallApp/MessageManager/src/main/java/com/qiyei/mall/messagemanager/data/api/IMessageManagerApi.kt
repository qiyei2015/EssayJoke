package com.qiyei.mall.messagemanager.data.api

import com.qiyei.framework.data.protocol.BaseResp
import com.qiyei.mall.messagemanager.data.bean.Message
import io.reactivex.Observable
import retrofit2.http.POST


/**
 * @author Created by qiyei2015 on 2018/10/27.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IMessageManagerApi {

    /**
     * 获取消息列表
     */
    @POST("msg/getList")
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>>

}