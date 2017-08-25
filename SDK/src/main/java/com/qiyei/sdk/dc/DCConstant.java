package com.qiyei.sdk.dc;

import com.qiyei.sdk.common.RuntimeEnv;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/20.
 * Version: 1.0
 * Description: 数据中心的常量
 */
public class DCConstant {
    /**
     * 调试用的标志
     */
    public static final String TAG = "DC";
    /**
     * 数据库类型的数据
     */
    public static final String SQL_DATA = "sql_data";
    /**
     * 数据库类型的数据
     */
    public static final String SP_DATA = "sp_data";
    /**
     * 内存类型的数据
     */
    public static final String MEM_DATA = "mem_data";
    /**
     * 加密标志
     */
    private static final String SECRET = "_sec";
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

//    public static class SQLDATA{
//
//    }
//
//    public static class
}
