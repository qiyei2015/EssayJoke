package com.qiyei.sdk.dc.impl;

import com.qiyei.sdk.common.RuntimeEnv;

/**
 * @author : 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/20.
 * Version: 1.0
 * Description: 数据中心的常量 ,继承内部类有利于解耦
 */
public class DC {
    /**
     * 调试用的标志
     */
    public static final String TAG = "DC";

    /**
     * 加密标志
     */
    static final String SECRET = "_sec";

    /**
     * 不加密数据的主机
     */
    static String AUTHORITY = RuntimeEnv.packageName;
    /**
     * 加密数据的主机
     */
    static String AUTHORITY_SECRET = RuntimeEnv.packageName + SECRET;
    /**
     * 不加密的uri
     */
    static String URI = "content://" + AUTHORITY;
    /**
     * 加密的uri
     */
    static String URI_SECRET = "content://" + AUTHORITY_SECRET;

    /**
     * 数据库类型的数据
     */
    static final String SQL_DATA = "sql_data";

    /**
     * SP类型的数据，多用户共享
     */
    static final String STORE_DATA = "store_data";

    /**
     * 用户数据类型数据，存储SP中
     */
    static final String USER_DATA = "user_data";

    /**
     * 内存类型的数据
     */
    static final String MEM_DATA = "mem_data";

    /**
     * 默认数据，存储在SP中
     */
    static final String DEFAULT_DATA = "default_data";

//    /**
//     * 存储在数据库中数据
//     */
//    public static class SqlData{
//
//    }

    /**
     * 存储在SP中，多用户共享数据
     */
    public static class StoreData{

    }

    /**
     * 用户数据存储在SP中，单一用户共享
     */
    public static class UserData{

    }

    /**
     * 运行时数据
     */
    public static class MemoryData{

    }

}
