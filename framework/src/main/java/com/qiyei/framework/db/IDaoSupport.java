package com.qiyei.framework.db;

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
    void init(SQLiteDatabase database , Class<T> clazz);

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
     * 查询所有
     * @return
     */
    List<T> queryAll();

}
