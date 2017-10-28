package com.qiyei.appdemo.net;

/**
 * @author Created by qiyei2015 on 2017/10/28.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 数据请求的基类
 */
public class RequestObject<T> {

    /**
     * 系统类型 android ios
     */
    private String systemType;

    /**
     * 设备ID 或MAC地址
     */
    private String deviceID;

    /**
     * 系统版本号 2017000000
     */
    private String versionCode;

    /**
     * 系统版本 1.0
     */
    private String versionName;

    /**
     * 应用App 名字
     */
    private String appName;

    /**
     * 服务器分析的数据
     */
    private MMVInfo mmv;

    /**
     * 请求内容
     */
    private T content;

    /**
     * @return {@link #systemType}
     */
    public String getSystemType() {
        return systemType;
    }

    /**
     * @param systemType the {@link #systemType} to set
     */
    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    /**
     * @return {@link #deviceID}
     */
    public String getDeviceID() {
        return deviceID;
    }

    /**
     * @param deviceID the {@link #deviceID} to set
     */
    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    /**
     * @return {@link #versionCode}
     */
    public String getVersionCode() {
        return versionCode;
    }

    /**
     * @param versionCode the {@link #versionCode} to set
     */
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    /**
     * @return {@link #versionName}
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * @param versionName the {@link #versionName} to set
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    /**
     * @return {@link #appName}
     */
    public String getAppName() {
        return appName;
    }

    /**
     * @param appName the {@link #appName} to set
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * @return {@link #mmv}
     */
    public MMVInfo getMmv() {
        return mmv;
    }

    /**
     * @param mmv the {@link #mmv} to set
     */
    public void setMmv(MMVInfo mmv) {
        this.mmv = mmv;
    }

    /**
     * @return {@link #content}
     */
    public T getContent() {
        return content;
    }

    /**
     * @param content the {@link #content} to set
     */
    public void setContent(T content) {
        this.content = content;
    }
}
