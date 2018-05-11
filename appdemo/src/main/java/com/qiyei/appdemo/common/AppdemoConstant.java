package com.qiyei.appdemo.common;

import com.qiyei.sdk.dc.impl.DCConstant;

/**
 * @author Created by qiyei2015 on 2018/5/12.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class AppdemoConstant {

    /**
     * 存储在SP中，多用户共享数据
     */
    public static class StoreData extends DCConstant.StoreData{
        public static final String KEY_11 = "11";
    }

    /**
     * 用户数据存储在SP中，单一用户共享
     */
    public static class UserData extends DCConstant.UserData{
        public static final String KEY_12 = "12";
    }

    /**
     * 运行时数据
     */
    public static class MemoryData extends DCConstant.MemoryData{
        public static final String KEY_13 = "13";
    }

    public static class DefaultData{
        public static final String KEY_14 = "14";
        public static final String KEY_15 = "15";
    }
}
