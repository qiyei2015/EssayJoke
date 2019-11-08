package com.qiyei.framework.rx

/**
 * @author Created by qiyei2015 on 2018/9/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class BaseException(val status:Int,val msg:String):Throwable() {

    override fun toString(): String {
        return "BaseException(status=$status, msg='$msg')"
    }
}