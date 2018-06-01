package com.qiyei.appdemo.model;

/**
 * @author Created by qiyei2015 on 2017/12/15.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MainMenu {

    public MainMenu() {
    }

    public MainMenu(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public MainMenu(int id, String name, Class<?> clazz) {
        this.id = id;
        this.name = name;
        this.clazz = clazz;
    }

    /**
     * id
     */
    private int id;

    /**
     * name
     */
    private String name;

    private Class<?> clazz;

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

    /**
     * @return {@link #clazz}
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * @param clazz the {@link #clazz} to set
     */
    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
