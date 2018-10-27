package com.qiyei.mall.messagemanager.service.impl

import com.qiyei.framework.extend.baseRespConvert
import com.qiyei.mall.messagemanager.data.bean.Message
import com.qiyei.mall.messagemanager.data.repository.MessageManagerRepository
import com.qiyei.mall.messagemanager.service.IMessageManagerService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/27.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class MessageManagerServiceImpl @Inject constructor() :IMessageManagerService {

    @Inject
    lateinit var mMessageManagerRepository: MessageManagerRepository

    override fun getMessageList(): Observable<MutableList<Message>?> {
        return mMessageManagerRepository.getMessageList().baseRespConvert()
    }

}