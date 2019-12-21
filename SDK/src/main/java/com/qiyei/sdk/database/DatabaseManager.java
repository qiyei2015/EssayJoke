package com.qiyei.sdk.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;

import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.database.engine.sqlite.SQLiteDatabaseEngine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by qiyei2015 on 2017/9/9.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 数据库管理者
 */
public class DatabaseManager implements IDatabaseManager {

    /**
     * 全局Context
     */
    private Context mContext;

    /**
     * 数据库引擎
     */
    private IDatabaseEngine mEngine;

    private Map<String,RoomDatabase> mRoomDatabaseMap;

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
        mRoomDatabaseMap = new HashMap<>();
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

    public void init(Context context){
        mContext =context;
    }

    /**
     * 注册数据库
     * @param clazz
     * @param db
     * @param migrations
     * @param <T>
     */
    public <T extends RoomDatabase> void registerRoomDatabase(Class<T> clazz,String db,@NonNull Migration... migrations){
        T database = Room.databaseBuilder(mContext.getApplicationContext()
                ,clazz,db)
                .addMigrations(migrations)
                .build();
        mRoomDatabaseMap.put(clazz.getCanonicalName(),database);
    }

    /**
     * 获取对应的Database实例
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends RoomDatabase> T getRoomDatabase(Class<T> clazz){
        if (mRoomDatabaseMap.get(clazz.getCanonicalName()) == null){
            throw new IllegalArgumentException("clazz is illegal");
        }
        return (T) mRoomDatabaseMap.get(clazz.getCanonicalName());
    }
}
