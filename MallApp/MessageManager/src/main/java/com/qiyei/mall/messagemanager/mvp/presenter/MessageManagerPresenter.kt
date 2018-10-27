package com.qiyei.mall.messagemanager.mvp.presenter

import com.qiyei.framework.extend.execute
import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.framework.rx.BaseObserver
import com.qiyei.mall.messagemanager.data.bean.Message
import com.qiyei.mall.messagemanager.mvp.view.IMessageManagerView
import com.qiyei.mall.messagemanager.service.IMessageManagerService
import com.qiyei.sdk.log.LogManager
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class MessageManagerPresenter @Inject constructor():BasePresenter<IMessageManagerView>() {

    @Inject
    lateinit var mMessageManagerService:IMessageManagerService

    override fun getTAG(): String {
        return MessageManagerPresenter::class.java.simpleName
    }

    /**
     * 获取消息列表
     */
    fun getMessageList(){
        mMessageManagerService.getMessageList().execute(object :BaseObserver<MutableList<Message>?>(mView){
            override fun onNext(item: MutableList<Message>?) {
                super.onNext(item)
                mView.onMessageListCallback(item)
            }
        },mLifecycleProvider)
    }
}