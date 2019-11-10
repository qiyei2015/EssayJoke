package com.qiyei.sdk.database;

/**
 * @author Created by qiyei2015 on 2017/11/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:  数据库管理接口
 */
public interface IDatabaseManager {

    /**
     * 创建及初始化数据库
     * @param dbName 数据库名称
     * @return true 初始化成功，false 失败
     */
    boolean initDatabase(String dbName);

    /**
     * 更新数据库
     * @param dbName
     * @param oldVersion
     * @param newVersion
     */
    void updateDatabase(String dbName,int oldVersion,int newVersion);

    /**
     * 删除数据库
     * @param dbName
     */
    void dropDatabase(String dbName);
}
