package com.qiyei.sdk.database;

/**
 * @author Created by qiyei2015 on 2017/9/9.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 数据库管理者
 */
public class DatabaseManager {


    /**
     * 单例方式提供对象
     */
    private static class SingleHolder{
        private static final DatabaseManager sInstance = new DatabaseManager();
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
     * @param dbName
     */
    public boolean createDatabase(String dbName){

        return false;
    }

    /**
     * 更新数据库
     * @param dbName
     * @param oldVersion
     * @param newVersion
     */
    public void undateDatabase(String dbName,int oldVersion,int newVersion){

    }

    /**
     * 设置数据路径
     * @param path
     */
    public void setDatabasePath(String path){

    }

    /**
     * 数据库引擎
     */
    public void setDatabaseEngine(IDatabaseEngine engine){

    }



    /**
     * 打开数据库会话，开始操作
     * @param dbName
     * @return
     */
    public IDBSession openSession(String dbName){
        //这里需要同步，否则将会出问题

        return null;
    }


}
