package com.qiyei.sdk.dc.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.qiyei.sdk.log.LogManager;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/26.
 * Version: 1.0
 * Description:
 */
public class SQLDataHelper extends SQLiteOpenHelper {

    /**
     * 构造方法
     * @param context
     * @param name
     * @param version
     */
    public SQLDataHelper(Context context, String name,int version) {
        super(context, name, null, version);
    }

    /**
     * 第一次创建调用
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLDataBuffer.SQL_CREATE_TABLE);

        LogManager.i(DC.TAG,"SQLDataHelper,  onCreate(SQLiteDatabase db) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLDataBuffer.SQL_UPDATE_TABLE);
        LogManager.i(DC.TAG,"SQLDataHelper,  onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) ");
    }

}
