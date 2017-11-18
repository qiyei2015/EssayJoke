package com.qiyei.sdk.database.engine.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.qiyei.sdk.database.DB;
import com.qiyei.sdk.database.IDBSession;
import com.qiyei.sdk.database.IQueryBuilder;
import com.qiyei.sdk.database.bean.DaoMaster;
import com.qiyei.sdk.log.LogManager;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Created by qiyei2015 on 2017/11/11.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class GreenDaoDBSession<T> implements IDBSession<T> {

    AbstractDao<T, ?> mDao;

    Class<T> mClazz;

    private DaoMaster.DevOpenHelper mDevOpenHelper;


    public GreenDaoDBSession(DaoMaster.DevOpenHelper helper, AbstractDao<T, ?> dao,Class<T> clazz) {
        mDevOpenHelper = helper;
        mDao = dao;
        mClazz = clazz;
        init(mDevOpenHelper.getWritableDatabase(),mClazz);
    }

    @Override
    public void init(SQLiteDatabase database, Class<T> clazz) {
        Class<? extends AbstractDao<?, ?>> daoClass = GreenDaoUtil.getDaoClass(clazz);
        if (daoClass != null){
            try {
                Field field = daoClass.getDeclaredField("TABLENAME");
                if (field != null){
                    field.setAccessible(true);
                    String tableName = (String) field.get(null);
                    //创建数据表XXXDao
                    createAllTables(clazz,mDevOpenHelper.getWritableDb(),isTableExist(database,tableName));
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public long insert(T t) {
        return mDao.insert(t);
    }

    @Override
    public long[] insert(List<T> list) {
        mDao.insertInTx(list);
        return new long[0];
    }

    @Override
    public IQueryBuilder queryBuilder() {

        QueryBuilder<T> builder = mDao.queryBuilder();

        List<T> list = builder.list();
        LogManager.i("DB",list.toString());

        // TODO: 2017/11/18 使用装饰器或者适配器模式进行转换？
        Database database = mDao.getDatabase();

        com.qiyei.sdk.database.QueryBuilder queryBuilder = new com.qiyei.sdk.database.QueryBuilder(mDevOpenHelper.getWritableDatabase(),mClazz);

        return queryBuilder;
    }

    @Override
    public long update(T obj, String whereClause, String... whereArgs) {
        return 0;
    }

    @Override
    public long delete(String whereClause, String... whereArgs) {
        return 0;
    }

    /**
     * 创建表
     * @param clazz
     * @param db
     * @param ifNotExists
     */
    private void createAllTables(Class<?> clazz,Database db, boolean ifNotExists){
        try {
            Class<? extends AbstractDao<?, ?>> daoClass = GreenDaoUtil.getDaoClass(clazz);
            Method method = daoClass.getDeclaredMethod("createTable",Database.class,boolean.class);

            if (method != null){
                method.setAccessible(true);
                method.invoke(null,db,ifNotExists);
            }
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除表
     * @param clazz
     * @param db
     * @param ifExists
     */
    private void dropAllTables(Class<?> clazz,Database db, boolean ifExists) {
        try {
            Class<? extends AbstractDao<?, ?>> daoClass = GreenDaoUtil.getDaoClass(clazz);
            for (Method method : daoClass.getDeclaredMethods()){
                LogManager.i(DB.TAG,"method:" + method.getName());
            }
            Method method = daoClass.getDeclaredMethod("dropTable",Database.class,boolean.class);
            if (method != null){
                method.setAccessible(true);
                method.invoke(null,db,ifExists);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断数据库中某个表是否存在
     * @param db
     * @param name
     * @return
     */
    private boolean isTableExist(SQLiteDatabase db,String name) {
        boolean isTableExist = false;
        Cursor c = db.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name= '" + name +"'", null);
        if(c.moveToNext()){
            int count = c.getInt(0);
            if(count > 0){
                isTableExist = true;
            }
        }
        c.close();
        db.close();
        return isTableExist;
    }

}
