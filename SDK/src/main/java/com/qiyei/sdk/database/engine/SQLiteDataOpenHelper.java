/**
 * @author Created by qiyei2015 on 2020/4/6.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: SqliteDataOpenHelper
 */

package com.qiyei.sdk.database.engine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;


public class SQLiteDataOpenHelper extends SQLiteOpenHelper {


    private static final String NAME = "user.db";
    public static final String USER_TABLE = "user";
    private static final int VERSION = 1;

    private String create_table = "create table "+ USER_TABLE + "(id Integer,name char(50),age Integer)";


    public SQLiteDataOpenHelper(@Nullable Context context) {
        super(context, NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
