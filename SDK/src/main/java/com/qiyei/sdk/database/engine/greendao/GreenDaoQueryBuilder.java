package com.qiyei.sdk.database.engine.greendao;

import com.qiyei.sdk.database.IQueryBuilder;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2017/11/20.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class GreenDaoQueryBuilder<T> implements IQueryBuilder<T> {

    /**
     * greenDao查询支持
     */
    private QueryBuilder<T> mQueryBuilder;

    /**
     * 构造方法
     * @param queryBuilder
     */
    public GreenDaoQueryBuilder(QueryBuilder<T> queryBuilder) {
        mQueryBuilder = queryBuilder;
    }

    @Override
    public IQueryBuilder columns(String... columns) {
        return this;
    }

    @Override
    public IQueryBuilder selectionArgs(String... selectionArgs) {
        return this;
    }

    @Override
    public IQueryBuilder having(String having) {
        return this;
    }

    @Override
    public IQueryBuilder orderBy(String orderBy) {
        return this;
    }

    @Override
    public IQueryBuilder limit(String limit) {

        return this;
    }

    @Override
    public IQueryBuilder groupBy(String groupBy) {

        return this;
    }

    @Override
    public IQueryBuilder selection(String selection) {
        return this;
    }

    @Override
    public List<T> query() {
        return mQueryBuilder.list();
    }

    @Override
    public List<T> queryAll() {
        return mQueryBuilder.list();
    }

}
