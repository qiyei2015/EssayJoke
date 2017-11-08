package com.qiyei.sdk.database;

/**
 * @author Created by qiyei2015 on 2017/9/9.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 数据库管理者
 */
public class DBManager implements IDBManager{

    /**
     * 单例方式提供对象
     */
    private static class SingleHolder{
        private static final DBManager sInstance = new DBManager();
    }

    /**
     * 内部类方式提供单例
     * @return
     */
    public static DBManager getInstance(){
        return SingleHolder.sInstance;
    }

    /**
     * 创建数据库
     * @param path 数据库路径
     * @param dbName 数据库名称
     */
    @Override
    public boolean initDatabase(String path,String dbName){

        return false;
    }

    /**
     * 更新数据库
     * @param dbName
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void updateDatabase(String dbName,int oldVersion,int newVersion){

    }

    /**
     * 删除数据库
     * @param dbName
     */
    @Override
    public void dropDatabase(String dbName) {

    }

    /**
     * 数据库引擎
     */
    public void setDatabaseEngine(IDBEngine engine){

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
