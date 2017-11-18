package com.qiyei.sdk.database.engine.greendao;

import com.qiyei.sdk.database.DB;
import com.qiyei.sdk.database.bean.DaoSession;
import com.qiyei.sdk.log.LogManager;


import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author Created by qiyei2015 on 2017/11/19.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class GreenDaoSession<T> extends AbstractDaoSession {

    private DaoConfig daoConfig;

    private AbstractDao<T, ?>  mDao;

    public GreenDaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap,Class<T> clazz) {
        super(db);

        Class<? extends AbstractDao<?, ?>> daoClass = GreenDaoUtil.getDaoClass(clazz);

        if (daoClass != null){
            daoConfig = daoConfigMap.get(GreenDaoUtil.getDaoClass(clazz)).clone();
            daoConfig.initIdentityScope(type);
            LogManager.i(DB.TAG,"daoConfig:" + daoConfig.tablename);
            try {
                Constructor constructor = daoClass.getDeclaredConstructor(DaoConfig.class);
                LogManager.i(DB.TAG,"constructor:" + constructor.getName());
                mDao = (AbstractDao<T, ?>) constructor.newInstance(daoConfig);

                Field field = AbstractDao.class.getDeclaredField("session");
                if (field != null){
                    field.setAccessible(true);
                    field.set(mDao,this);
                    registerDao(clazz, mDao);
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void clear() {
        daoConfig.clearIdentityScope();
    }

    /**
     * 获取Dao
     * @return
     */
    public AbstractDao<T, ?>  geDao() {
        return mDao;
    }

}
