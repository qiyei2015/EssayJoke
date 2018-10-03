package com.qiyei.mall.usermanager.data.protocol

/**
 * @author Created by qiyei2015 on 2018/10/3.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
data class EditUserInfoReq(val userIcon: String,
                           val userName: String,
                           val gender: String,
                           val sign: String)