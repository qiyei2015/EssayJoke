package com.qiyei.appdemo.net;

/**
 * @author Created by qiyei2015 on 2017/10/28.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class Bean2 {

    private String name;

    private String password;

    public Bean2() {
    }

    public Bean2(String name, String password) {
        this.name = name;
        this.password = password;
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
     * @return {@link #password}
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the {@link #password} to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Bean2{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
