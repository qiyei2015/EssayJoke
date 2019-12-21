package com.qiyei.framework.database.room;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.qiyei.sdk.database.DB;

/**
 * @author Created by qiyei2015 on 2019/12/22.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class AppData {

    /**
     * 数据库名称
     */
    public static final String DB_NAME = DB.DATABASE_PATH + "android.db";

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //此处执行数据库升级的逻辑:例如表新增字段或者新建表等其他升级操作

            //User表新增字段
            database.execSQL("ALTER TABLE User " + " ADD COLUMN sex TEXT");

        }
    };
}
