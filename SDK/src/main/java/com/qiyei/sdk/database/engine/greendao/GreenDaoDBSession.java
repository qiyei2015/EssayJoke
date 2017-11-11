package com.qiyei.sdk.database.engine.greendao;

import android.database.sqlite.SQLiteDatabase;

import com.qiyei.sdk.database.IDBSession;
import com.qiyei.sdk.db.QuerySupport;
import com.qiyei.sdk.log.LogManager;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2017/11/11.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class GreenDaoDBSession<T> implements IDBSession<T> {

    AbstractDao<T, ?> mDao;

    Class<T> mClazz;

    public GreenDaoDBSession(AbstractDao<T, ?> dao) {
        mDao = dao;
    }


    @Override
    public void init(SQLiteDatabase database, Class<T> clazz) {
        mClazz = clazz;

    }

    @Override
    public long insert(T t) {
        return mDao.insert(t);
    }

    @Override
    public long[] insert(List<T> list) {
        mDao.insertInTx(list);
        return new long[0];
    }

    @Override
    public QuerySupport querySupport() {

        QueryBuilder<T> builder = mDao.queryBuilder();

        List<T> list = builder.list();
        LogManager.i("DB",list.toString());

        return null;
    }

    @Override
    public long update(T obj, String whereClause, String... whereArgs) {
        return 0;
    }

    @Override
    public long delete(String whereClause, String... whereArgs) {
        return 0;
    }
}
