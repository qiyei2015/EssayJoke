package com.qiyei.sdk.dc.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.qiyei.sdk.common.RuntimeEnv;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/22.
 * Version: 1.0
 * Description: SQL 数据库数据
 */
public class SQLDataBuffer implements IDataBuffer {

    /**
     * 数据库名称
     */
    private static final String DB_NAME = RuntimeEnv.packageName + "_sql";
    /**
     * 表名称
     */
    private static final String TABLE_NAME = "data_sql";
    /**
     * 版本号
     */
    private static int DB_VERSION = 1;

    /**
     * 创建表的语句
     */
    static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " (" +
            $.id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            $.name + " TEXT, " +
            $.value + " TEXT " +
            " )";

    /**
     * 升级表的语句
     */
    static final String SQL_UPDATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " (" +
            $.id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            $.name + " TEXT, " +
            $.value + " TEXT " +
            " )";

    /**
     * 数据库对象
     */
    private SQLiteDatabase mDatabase;

    /**
     * 数据库的一些字段名称
     */
    interface ${
        //id
        String id = "id";

        /**
         * 名称
         */
        String name = "name";

        /**
         * 值
         */
        String value = "value";

    }

    /**
     * 同一个包下可以引用
     * @param context
     */
    SQLDataBuffer(Context context){
        mDatabase = new SQLDataHelper(context,DB_NAME,DB_VERSION).getWritableDatabase();
    }

    @Override
    public void setValue(String uri, String value) {

    }

    @Override
    public String getValue(String uri) {
        return null;
    }

    @Override
    public void deleteValue(String uri) {

    }

    


}
