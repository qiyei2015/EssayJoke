package com.qiyei.framework.constant

/**
 * @author Created by qiyei2015 on 2018/10/20.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:订单状态
 */
class OrderStatus {
    companion object {
        const val ORDER_ALL = 0 //全部
        const val ORDER_WAIT_PAY = 1 //待支付
        const val ORDER_WAIT_CONFIRM = 2 //待收货
        const val ORDER_COMPLETED = 3 //已完成
        const val ORDER_CANCELED = 4 //已取消
    }
}