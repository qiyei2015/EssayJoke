package com.qiyei.provider.router;

/**
 * @author Created by qiyei2015 on 2018/10/12.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:路由总表
 */
public interface RouteMall {

    interface UserManager{
        /**
         * 登录
         */
        String LOGIN = "/user_manager/login";

    }

    interface OrderManager{
        /**
         * 订单确认
         */
        String ORDER_CONFIRM = "/order_manager/order_confirm";

        /**
         * 订单列表
         */
        String ORDER_LIST = "/order_manager/order_list";
    }

    interface PayManager{
        /**
         * 订单支付
         */
        String CASH_PAY = "/pay_manager/cash_pay";

    }


}
