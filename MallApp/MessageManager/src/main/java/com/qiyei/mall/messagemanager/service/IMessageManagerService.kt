package com.qiyei.mall.messagemanager.service

import com.qiyei.mall.messagemanager.data.bean.Message
import io.reactivex.Observable

/**
 * @author Created by qiyei2015 on 2018/10/27.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IMessageManagerService {

    fun getMessageList(): Observable<MutableList<Message>?>
}