package com.qiyei.framework.constant

import com.qiyei.sdk.dc.impl.DC

/**
 * @author Created by qiyei2015 on 2018/10/20.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class OrderConstant{

    companion object {
        //支付订单操作
        const val OPT_ORDER_PAY = 1
        //确认订单操作
        const val OPT_ORDER_CONFIRM = 2
        //取消订单操作
        const val OPT_ORDER_CANCEL = 3
    }

    class User:DC.UserData(){
        companion object {
            //订单状态
            const val KEY_ORDER_STATUS = "order_status"
            //收货地址
            const val KEY_SHIP_ADDRESS = "ship_address"
            //选择收货地址请求码
            const val REQ_SELECT_ADDRESS = 1001
            //是否选择收货地址
            const val KEY_IS_SELECT_ADDRESS = "is_select_address"
            //是否编辑地址
            const val KEY_ADDRESS_IS_EDIT = "address_is_edit"

        }
    }


}