package com.qiyei.sdk.database.engine.greendao;

import android.database.sqlite.SQLiteDatabase;

import com.qiyei.sdk.database.test.bean.DaoMaster;
import com.qiyei.sdk.database.test.bean.DaoSession;
import com.qiyei.sdk.database.test.bean.UserDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

/**
 * @author Created by qiyei2015 on 2017/11/18.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class GreenDaoMaster extends DaoMaster {

    public static final String DAO = "Dao";

    /** Creates underlying database table using DAOs. */

    public static void createAllTables(Database db, boolean ifNotExists) {
        UserDao.createTable(db, ifNotExists);
    }

    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(Database db, boolean ifExists) {
        UserDao.dropTable(db, ifExists);
    }

    /**
     * 构造方法
     * @param db
     */
    public GreenDaoMaster(SQLiteDatabase db) {
        super(db);
    }

    /**
     * 构造方法
     * @param db
     * @param clazz
     */
    public GreenDaoMaster(Database db,Class<?> clazz) {
        super(db);
        //要在DaoMaster中实现registerDaoClass(XXXDao.class)
        try {
            Class<? extends AbstractDao<?, ?>> daoClass = (Class<? extends AbstractDao<?,?>>) Class.forName(clazz.getCanonicalName() + DAO);
            registerDaoClass(daoClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DaoSession newSession() {
        return super.newSession();
    }

    /**
     * 创建Session
     * @param clazz
     * @return
     */
    public AbstractDaoSession newSession(Class<?> clazz){

        return new GreenDaoSession(db, IdentityScopeType.Session, daoConfigMap,clazz);
    }

}
