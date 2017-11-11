package com.qiyei.sdk.database.engine.greendao;


import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import com.qiyei.sdk.database.IDBEngine;
import com.qiyei.sdk.database.IDBSession;
import com.qiyei.sdk.database.bean.DaoMaster;
import com.qiyei.sdk.database.bean.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

import java.io.File;

/**
 * @author Created by qiyei2015 on 2017/9/9.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 数据库引擎
 */
public class GreenDaoDBEngine implements IDBEngine {
    /**
     * 数据库路径
     */
    private String mDatabasePath;

    /**
     * 数据库名称
     */
    private String mDatabaseName;

    private DaoMaster.DevOpenHelper mDevOpenHelper;

    private DaoMaster mMaster;

    private DaoSession mDaoSession;

    @Override
    public boolean initDatabase(Context context,String path, String dbName) {
        mDatabasePath = path;
        mDatabaseName = dbName;
        mDevOpenHelper = new GreenDaoOpenHelper(context,mDatabaseName);
        mDevOpenHelper.getWritableDatabase();
        return true;
    }

    @Override
    public void updateDatabase(String dbName, int oldVersion, int newVersion) {

    }

    @Override
    public void dropDatabase(String dbName) {

    }

    @Override
    public <T> IDBSession<T> getDBSession(String dbName,Class<T> clazz) {
        //获取DaoMaster
        mMaster = new DaoMaster(mDevOpenHelper.getWritableDb());
        mDaoSession = mMaster.newSession();

        // TODO: 2017/11/11 具体的业务逻辑相关 ,可能需要使用装饰器模式，新建一个IDBSession对象来包装它
        AbstractDao<?,?> dao = mDaoSession.getDao(clazz);
        return new GreenDaoDBSession(dao);
    }

    /**
     * 初始化数据库
     */
    private class GreenDaoOpenHelper extends DaoMaster.DevOpenHelper{

        GreenDaoOpenHelper(Context context,String name){
            super(new GreenDaoContextWrapper(mDatabasePath,context),name);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            super.onUpgrade(db, oldVersion, newVersion);
        }
    }

    /**
     * 自定义Context修改数据库存储路径
     */
    private class GreenDaoContextWrapper extends ContextWrapper{
        /**
         * 数据库路径
         */
        private String mPath;

        public GreenDaoContextWrapper(String path,Context base) {
            super(base);
            mPath = path;
        }

        @Override
        public File getDatabasePath(String name) {
            File file = new File(mPath,name);
            return file;
        }

        @Override
        public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
            return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name),factory);
        }

        @Override
        public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
            return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name),factory);
        }
    }
}
