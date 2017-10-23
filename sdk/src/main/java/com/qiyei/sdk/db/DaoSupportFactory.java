package com.qiyei.sdk.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.qiyei.sdk.common.RuntimeEnv;
import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.util.AndroidUtil;
import com.qiyei.sdk.util.ToastUtil;

import java.io.File;
import java.io.IOException;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/3.
 * Version: 1.0
 * Description:
 */
public class DaoSupportFactory {

    private static final String TAG = DaoSupportFactory.class.getSimpleName();

    /**
     * 数据库
     */
    private SQLiteDatabase mDatabase;

    /**
     * 构造方法，创建数据库位置
     */
    private DaoSupportFactory(String name){

        //数据库放在外部存储下面：/sdcard/EasyJoke/database/        android6.0以后需要动态申请权限
        File dbDir = new File(AndroidUtil.getExternalDataPath() + File.separator + "database");
        if (!dbDir.exists()){
            dbDir.mkdirs();
        }

        File dbFile = new File(dbDir,name);
        LogManager.d(TAG,"dbFile --> " + dbFile);

        //如果存在就删除文件
        if (dbFile.exists()){
            dbFile.delete();
        }

        boolean create = false;
        try {
            create = dbFile.createNewFile();
        } catch (IOException e) {
            create = false;
            e.printStackTrace();
        }

        if (create){
            //打开创建数据库
            mDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile,null);
            if (mDatabase == null){
                ToastUtil.showLongToast("数据库创建失败");
                LogManager.e(TAG,"数据库创建失败");
            }
        }else {
            ToastUtil.showLongToast("数据库创建失败");
            LogManager.e(TAG,"数据库创建失败");
        }
    }

    /**
     * 单例方式提供对象
     */
    private static class SingleHolder{
        private static final DaoSupportFactory sInstance = new DaoSupportFactory("essayjoke.db");
    }

    /**
     * 内部类方式提供单例
     * @return
     */
    public static DaoSupportFactory getInstance(){
        return SingleHolder.sInstance;
    }

    /**
     * 返回T 对应的Dao,用于数据的操作
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> IDaoSupport<T> getDao(Class<T> clazz){
        IDaoSupport<T> daoSupport = new DaoSupport();
        daoSupport.init(mDatabase,clazz);
        return daoSupport;
    }

}
