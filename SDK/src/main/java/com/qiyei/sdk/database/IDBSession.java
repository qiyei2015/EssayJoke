package com.qiyei.sdk.database;

import android.database.sqlite.SQLiteDatabase;

import com.qiyei.sdk.db.QuerySupport;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2017/9/9.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 数据库会话，主要会完成数据的建表，查询，删除等操作
 */
public interface IDBSession<T> {

    /**
     * 初始化
     * @param database
     * @param clazz
     */
    void init(SQLiteDatabase database, Class<T> clazz);

    /**
     * 插入，返回受影响的行数
     * @param t
     * @return
     */
    long insert(T t);

    /**
     * 批量插入，返回受影响的行数
     * @param list
     * @return
     */
    long[] insert(List<T> list);

    /**
     * 获取查询支持
     * @return
     */
    QuerySupport querySupport();

    /**
     * 更新
     * @param obj
     * @param whereClause
     * @param whereArgs
     * @return
     */
    long update(T obj, String whereClause, String... whereArgs);

    /**
     * 删除
     * @param whereClause
     * @param whereArgs
     * @return
     */
    long delete(String whereClause, String... whereArgs);
}
