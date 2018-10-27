package com.qiyei.mall.messagemanager.provider

import android.content.Context
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.facade.annotation.Route
import com.qiyei.provider.service.mall.IPushManagerService
import com.qiyei.provider.service.mall.MallServiceConstant
import com.qiyei.sdk.log.LogManager

/**
 * @author Created by qiyei2015 on 2018/10/27.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Route(path = MallServiceConstant.PUSH_MANAGER_SERVICE_PATH ,name = MallServiceConstant.PUSH_MANAGER_SERVICE_NAME)
class PushManagerServiceImpl:IPushManagerService {

    private lateinit var mContext:Context

    override fun init(context: Context) {
        mContext = context
    }

    override fun getTAG(): String {
        return this::class.java.simpleName
    }

    override fun getPushId(): String {
        val pushId = JPushInterface.getRegistrationID(mContext)
        LogManager.i(getTAG(),"pushId:$pushId")
        return pushId
    }

}