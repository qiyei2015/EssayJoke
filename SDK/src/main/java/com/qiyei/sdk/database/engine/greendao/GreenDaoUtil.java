package com.qiyei.sdk.database.engine.greendao;

import com.qiyei.sdk.database.DB;
import com.qiyei.sdk.log.LogManager;

import org.greenrobot.greendao.AbstractDao;

import static com.qiyei.sdk.database.engine.greendao.GreenDaoMaster.DAO;

/**
 * @author Created by qiyei2015 on 2017/11/19.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class GreenDaoUtil {

    public static Class<? extends AbstractDao<?, ?>> getDaoClass(Class<?> clazz){
        try {
            Class<? extends AbstractDao<?, ?>> daoClass = (Class<? extends AbstractDao<?,?>>) Class.forName(clazz.getCanonicalName() + DAO);
            LogManager.i(DB.TAG,"daoClass:" + daoClass.getName());
            return daoClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
