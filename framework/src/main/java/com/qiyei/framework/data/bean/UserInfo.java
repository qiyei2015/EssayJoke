package com.qiyei.framework.data.bean;

/**
 * @author Created by qiyei2015 on 2017/10/28.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class UserInfo {

    private String name;

    private String password;


    private String nickName;

    private String email;

    public UserInfo() {
    }

    public UserInfo(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public UserInfo(String name, String password, String nickName, String email) {
        this.name = name;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
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

    /**
     * @return {@link #nickName}
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName the {@link #nickName} to set
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return {@link #email}
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the {@link #email} to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Bean2{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
