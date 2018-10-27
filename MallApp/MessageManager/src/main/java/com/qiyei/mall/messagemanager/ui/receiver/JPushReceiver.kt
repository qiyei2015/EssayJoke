package com.qiyei.mall.messagemanager.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.qiyei.provider.event.UpdateMessageEvent
import com.qiyei.provider.router.RouteMall
import com.qiyei.provider.router.RouterMallConstant
import com.qiyei.sdk.log.LogManager
import org.json.JSONObject


/**
 * @author Created by qiyei2015 on 2018/10/27.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class JPushReceiver:BroadcastReceiver() {

    companion object {
        const val TAG = "JPushReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.extras
        LogManager.d(TAG, "onReceive - " + intent?.action)

        when(intent?.action){

            //注册
            JPushInterface.ACTION_REGISTRATION_ID -> {
                val regId = bundle?.getString(JPushInterface.EXTRA_REGISTRATION_ID)
                LogManager.d(TAG, "接收 Registration Id : " + regId!!)
            }

            //自定义消息
            JPushInterface.ACTION_MESSAGE_RECEIVED -> {
                LogManager.d(TAG, "收到了自定义消息。消息内容是：" + bundle?.getString(JPushInterface.EXTRA_MESSAGE)!!)
                Bus.send(UpdateMessageEvent(true))
            }

            // 在这里可以做些统计，或者做些其他工作
            JPushInterface.ACTION_NOTIFICATION_RECEIVED -> {
                LogManager.d(TAG, "收到了通知")
            }

            //通知被打开 在这里可以自己写代码去定义用户点击后的行为
            JPushInterface.ACTION_NOTIFICATION_OPENED -> {
                LogManager.d(TAG, "用户点击打开了通知")
                val extra = bundle?.getString(JPushInterface.EXTRA_EXTRA)
                val json = JSONObject(extra)
                val orderId = json.getInt("orderId")
                ARouter.getInstance().build(RouteMall.OrderManager.ORDER_DETAIL)
                        .withInt(RouterMallConstant.KEY_ORDER_ID,orderId)
                        .navigation()
            }
        }

    }

}