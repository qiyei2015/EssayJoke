package com.qiyei.opensource.data.bean;

import javax.inject.Inject;

/**
 * @author Created by qiyei2015 on 2018/6/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class UserModel {

    int id;

    String name;

    public UserModel() {
        id = 10;
        name = "hello";
    }

    /**
     * @return {@link #id}
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the {@link #id} to set
     */
    public void setId(int id) {
        this.id = id;
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
}
