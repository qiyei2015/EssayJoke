package com.qiyei.sdk.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.database.engine.room.AppDatabase;
import com.qiyei.sdk.database.engine.room.User;
import com.qiyei.sdk.database.engine.room.UserDao;
import com.qiyei.sdk.database.engine.sqlite.SQLiteDatabaseEngine;

/**
 * @author Created by qiyei2015 on 2017/9/9.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 数据库管理者
 */
public class DatabaseManager implements IDatabaseManager {

    /**
     * 数据库引擎
     */
    private IDatabaseEngine mEngine;

    /**
     * 单例方式提供对象
     */
    private static class SingleHolder{
        private static final DatabaseManager sInstance = new DatabaseManager();
    }

    /**
     * 构造方法
     */
    private DatabaseManager(){
        initEngine(new SQLiteDatabaseEngine());
    }

    /**
     * 内部类方式提供单例
     * @return
     */
    public static DatabaseManager getInstance(){
        return SingleHolder.sInstance;
    }

    /**
     * 创建数据库
     * @param dbName 数据库名称
     */
    @Override
    public boolean initDatabase(String dbName){
        String path = "";
        return mEngine.initDatabase(RuntimeEnv.appContext,path,dbName);
    }

    /**
     * 更新数据库
     * @param dbName
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void updateDatabase(String dbName,int oldVersion,int newVersion){
        mEngine.updateDatabase(dbName,oldVersion,newVersion);
    }

    /**
     * 删除数据库
     * @param dbName
     */
    @Override
    public void dropDatabase(String dbName) {
        mEngine.dropDatabase(dbName);
    }

    /**
     * 数据库引擎
     */
    protected void initEngine(IDatabaseEngine engine){
        mEngine = engine;
    }

    /**
     * 打开数据库会话，开始操作
     * @param dbName
     * @return
     */
    public <T> IDatabaseSession<T> openSession(String dbName, Class<T> clazz){
        return mEngine.getDBSession(dbName,clazz);
    }

    public void testRoom(Context context){
        AppDatabase database = Room.databaseBuilder(context.getApplicationContext()
                ,AppDatabase.class,"qiyei.db")
                .addMigrations(MIGRATION_1_2)
                .build();

        UserDao userDao = database.userDao();
        User user = new User();
        user.setFirstName("大爷");
        user.setLastName("嘿嘿");

        new Thread(new Runnable() {
            @Override
            public void run() {
                userDao.insertAll(user);
            }
        }).start();

    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //此处执行数据库升级的逻辑:例如表新增字段或者新建表等其他升级操作

            //User表新增字段
            database.execSQL("ALTER TABLE User " + " ADD COLUMN sex TEXT");

        }
    };
}
