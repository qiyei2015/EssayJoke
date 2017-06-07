package com.qiyei.sdk.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/3.
 * Version: 1.0
 * Description:
 */
public interface IDaoSupport<T> {

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
