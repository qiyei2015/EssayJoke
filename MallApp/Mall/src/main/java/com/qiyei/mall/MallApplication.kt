package com.qiyei.mall


import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.launcher.ARouter
import com.qiyei.framework.FrameworkApplication


/**
 * @author Created by qiyei2015 on 2018/9/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class MallApplication :FrameworkApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {  // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化

        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
    }

}