package com.qiyei.appdemo.model;

import org.greenrobot.greendao.annotation.Entity;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/3.
 * Version: 1.0
 * Description:
 */
public class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the {@link #name} to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return {@link #age}
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the {@link #age} to set
     */
    public void setAge(int age) {
        this.age = age;
    }
}
