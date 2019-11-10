package com.qiyei.framework.data.protocol

/**
 * @author Created by qiyei2015 on 2018/9/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 公共数据响应体
 */
class BaseResp<out T>(val status:Int,val message:String,val data:T) {

}