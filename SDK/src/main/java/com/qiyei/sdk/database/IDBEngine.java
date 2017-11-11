package com.qiyei.sdk.database;

import android.content.Context;



/**
 * @author Created by qiyei2015 on 2017/9/9.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 数据库引擎
 */
public interface IDBEngine {

    /**
     * 初始化数据库
     * @param context
     * @param path
     * @param dbName
     * @return
     */
    boolean initDatabase(Context context,String path, String dbName);

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

    /**
     * 获取数据库操作的Session
     * @param dbName
     * @return
     */
    <T> IDBSession<T> getDBSession(String dbName,Class<T> clazz);
}
