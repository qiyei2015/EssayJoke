package com.qiyei.framework.constant

import com.qiyei.sdk.dc.impl.DC

/**
 * @author Created by qiyei2015 on 2018/9/23.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class MallConstant : DC.UserData(){

    /**
     * 伴生对象
     */
    companion object {
        //七牛服务器地址
        const val IMAGE_SERVER_ADDRESS = "http://osea2fxp7.bkt.clouddn.com/"
        //服务器地址 本机IP 默认验证码是123456
        const val SERVER_ADDRESS = "http://192.168.1.108:8080"
        //Token Key
        const val KEY_SP_TOKEN = "token"
    }

}