package com.qiyei.sdk.database.engine.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * @author Created by qiyei2015 on 2019/12/19.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Database(entities = {User.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
