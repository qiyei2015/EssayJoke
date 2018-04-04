package com.qiyei.sdk.database.test.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

/**
 * @author Created by qiyei2015 on 2017/11/11.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Entity
public class User {
    int id;
    String name;
    @Keep()
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @Keep()
    public User() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
