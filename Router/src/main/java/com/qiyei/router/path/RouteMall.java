package com.qiyei.router.path;

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
        String path_login = "/user_manager/login";

    }

    interface OrderManager{
        /**
         * 订单确认
         */
        String order_confirm = "/order_manager/order_confirm";

        /**
         * 订单列表
         */
        String order_list = "/order_manager/order_list";
    }

    
}
