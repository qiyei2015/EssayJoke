package com.qiyei.sdk.database.engine.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.qiyei.sdk.database.DB;
import com.qiyei.sdk.database.IDatabaseEngine;
import com.qiyei.sdk.database.IDatabaseSession;
import com.qiyei.sdk.log.LogManager;

import java.io.File;
import java.io.IOException;

/**
 * @author Created by qiyei2015 on 2017/11/20.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class SqliteDatabaseEngine implements IDatabaseEngine{

    /**
     * 数据库路径
     */
    private String mDatabasePath;

    /**
     * 数据库名称
     */
    private String mDatabaseName;


    @Override
    public boolean initDatabase(Context context, String path, String dbName) {
        mDatabasePath = path;
        mDatabaseName = dbName;

        boolean flag = false;

        //数据库放在Android/data下
        File dbDir = new File(DB.DATABASE_PATH + mDatabasePath);

        if (!dbDir.exists()){
            dbDir.mkdirs();
        }

        File dbFile = new File(dbDir,mDatabaseName);
        LogManager.i(DB.TAG,"dbFile --> " + dbFile);

        //如果存在就删除文件
        if (dbFile.exists()){
            dbFile.delete();
        }

        boolean create = false;

        try {
            create = dbFile.createNewFile();
        } catch (IOException e) {
            create = false;
            e.printStackTrace();
        }

        if (create){
            //打开创建数据库
            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(dbFile,null);
            if (database != null){
                flag = true;
                return flag;
            }
        }

        LogManager.e(DB.TAG,"create database fail:" + dbName);
        return flag;
    }

    @Override
    public void updateDatabase(String dbName, int oldVersion, int newVersion) {

    }

    @Override
    public void dropDatabase(String dbName) {

    }

    @Override
    public <T> IDatabaseSession<T> getDBSession(String dbName, Class<T> clazz) {
        SQLiteDatabase database = null;
        if (TextUtils.isEmpty(mDatabasePath)){
            database = SQLiteDatabase.openDatabase(DB.DATABASE_PATH + dbName,null,SQLiteDatabase.OPEN_READWRITE);
        }else {
            database = SQLiteDatabase.openDatabase(DB.DATABASE_PATH + mDatabasePath + File.separator + dbName,null,SQLiteDatabase.OPEN_READWRITE);
        }
        return new SqliteDatabaseSession<T>(database,clazz);
    }


}
