package com.qiyei.mall.messagemanager.mvp.view

import com.qiyei.framework.mvp.view.IBaseView
import com.qiyei.mall.messagemanager.data.bean.Message

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
interface IMessageManagerView:IBaseView {

    fun onMessageListCallback(list:MutableList<Message>?)
}