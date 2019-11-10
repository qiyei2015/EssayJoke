package com.qiyei.provider.common

import com.alibaba.android.arouter.launcher.ARouter
import com.qiyei.framework.constant.MallConstant
import com.qiyei.provider.router.RouteMall
import com.qiyei.sdk.dc.DataManager

/**
 * @author Created by qiyei2015 on 2018/10/12.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */

/**
 * 是否已经登录
 */
fun isLogin():Boolean{
    return DataManager.getInstance().getString(MallConstant::class.java, MallConstant.KEY_SP_TOKEN,null) != null
}

fun afterLogin(method: () -> Unit){
    if (isLogin()){
        method()
    }else {
        //先登录
        ARouter.getInstance().build(RouteMall.UserManager.LOGIN).navigation()
    }
}