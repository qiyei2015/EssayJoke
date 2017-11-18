package com.qiyei.sdk.database;


import java.util.List;

/**
 * @author Created by qiyei2015 on 2017/11/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 查询支持
 */
public interface IQueryBuilder<T> {

    // TODO: 2017/11/18 接口参考greenDao的设计

    /**
     * 设置查询的行数
     * @param columns
     * @return
     */
    IQueryBuilder columns(String... columns);

    IQueryBuilder selectionArgs(String... selectionArgs);

    IQueryBuilder having(String having);

    IQueryBuilder orderBy(String orderBy);

    IQueryBuilder limit(String limit);

    IQueryBuilder groupBy(String groupBy);

    IQueryBuilder selection(String selection);

    /**
     * 返回查询条件说查询到的
     * @return
     */
    List<T> query();

    /**
     * 查询所有
     * @return
     */
    List<T> queryAll();

}
