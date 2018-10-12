package com.qiyei.router.util

import com.alibaba.android.arouter.launcher.ARouter
import com.qiyei.router.path.RouteMall

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
    return false
}

fun afterLogin(method: () -> Unit){
    if (isLogin()){
        method()
    }else {
        //先登录
        ARouter.getInstance().build(RouteMall.UserManager.path_login).navigation()
    }
}