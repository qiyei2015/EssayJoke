package com.qiyei.framework.database.room;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import java.util.List;

/**
 * @author Created by qiyei2015 on 2019/12/19.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<com.qiyei.framework.database.room.User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<com.qiyei.framework.database.room.User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
    com.qiyei.framework.database.room.User findByName(String first, String last);

    @Query("SELECT * FROM user ORDER BY uid")
    DataSource.Factory<Integer,User> getAllByUid();

    @Insert
    void insertAll(com.qiyei.framework.database.room.User... users);

    @Delete
    void delete(com.qiyei.framework.database.room.User user);
}
