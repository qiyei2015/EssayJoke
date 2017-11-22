package com.qiyei.sdk.database.engine.sqlite;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;

import com.qiyei.sdk.database.DB;
import com.qiyei.sdk.database.IDatabaseSession;
import com.qiyei.sdk.database.IQueryBuilder;
import com.qiyei.sdk.db.DaoUtil;
import com.qiyei.sdk.log.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author Created by qiyei2015 on 2017/9/9.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 数据库会话，主要会完成数据的建表，查询，删除等操作
 */
public class SQLiteDatabaseSession<T> implements IDatabaseSession<T>{

    /**
     * 数据库
     */
    private SQLiteDatabase mDatabase;
    /**
     * 泛型类
     */
    private Class<T> mClass;
    /**
     * 反射时方法参数
     */
    private Object[] mMethodArgs = new Object[2];
    /**
     * 反射时的方法缓存
     */
    private Map<String,Method> mMethodMap = new ArrayMap<>();

    /**
     * 构造方法
     * @param database
     * @param clazz
     */
    public SQLiteDatabaseSession(SQLiteDatabase database, Class<T> clazz) {
        mDatabase = database;
        mClass = clazz;
        init(mDatabase,mClass);
    }

    @Override
    public void init(SQLiteDatabase database, Class<T> clazz) {
        mDatabase = database;
        mClass = clazz;

        //建表，表名字就是类名字
        /*"create table if not exists Person ("
                + "id integer primary key autoincrement, "
                + "name text, "
                + "age integer, "
                + "flag boolean)";*/

        StringBuffer sb = new StringBuffer();

        Field idField = null;
        try {
            idField = clazz.getDeclaredField("id");
            if (idField != null){
                sb.append("create table if not exists ")
                        .append(DaoUtil.getTableName(mClass))
                        .append(" (");
                // TODO: 2017/11/20 可以对id做处理，让其为主键 
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            sb.append("create table if not exists ")
                    .append(DaoUtil.getTableName(mClass))
                    .append("(id integer primary key autoincrement, ");
        }
        
        for (Field field : mClass.getDeclaredFields()){
            field.setAccessible(true);
            String name = field.getName();
            String type = field.getType().getName();
            sb.append(name).append(" " + DaoUtil.getColumnType(type)).append(", ");
        }

        sb.replace(sb.length() - 2, sb.length(), ")");

        LogManager.i(DB.TAG,"create table --> " + sb.toString());

        mDatabase.execSQL(sb.toString());

    }

    @Override
    public long insert(T t) {
        ContentValues values = buildContentValues(t);
        return mDatabase.insert(DaoUtil.getTableName(mClass),null,values);
    }

    @Override
    public long[] insert(List<T> list) {
        long[] result = new long[list.size()];
        int i = 0;

        //开启SQLite事务
        mDatabase.beginTransaction();
        for (T obj : list){
            result[i] = insert(obj);
        }
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();

        return result;
    }

    @Override
    public IQueryBuilder<T> queryBuilder() {
        SQLiteQueryBuilder<T> queryBuilder = new SQLiteQueryBuilder<>(mDatabase,mClass);
        return queryBuilder;
    }

    @Override
    public long update(T obj, String whereClause, String... whereArgs) {
        ContentValues values = buildContentValues(obj);
        return mDatabase.update(DaoUtil.getTableName(mClass),values,whereClause,whereArgs);
    }

    @Override
    public long delete(String whereClause, String... whereArgs) {
        return mDatabase.delete(DaoUtil.getTableName(mClass),whereClause,whereArgs);
    }

    /**
     * 创建T对象的ContentValues
     * @param obj
     * @return
     */
    private ContentValues buildContentValues(T obj){
        ContentValues contentValues = new ContentValues();

        Field[] fields = mClass.getDeclaredFields();

        for (Field field : fields){
            try {
                field.setAccessible(true);

                String key = field.getName();
                //获取key对应的值
                Object value = field.get(obj);

                mMethodArgs[0] = key;
                mMethodArgs[1] = value;

                String fileTypeName = field.getType().getName();

                Method method = mMethodMap.get(fileTypeName);
                if (method == null){
                    //获取put方法
                    method = ContentValues.class.getMethod("put",String.class,value.getClass());
                    mMethodMap.put(fileTypeName,method);
                }
                method.invoke(contentValues,mMethodArgs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } finally {
                mMethodArgs[0] = null;
                mMethodArgs[1] = null;
            }
        }
        return contentValues;
    }

}
