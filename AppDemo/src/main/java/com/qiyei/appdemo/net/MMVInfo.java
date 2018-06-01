package com.qiyei.appdemo.net;

/**
 * @author Created by qiyei2015 on 2017/10/28.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 服务器数据分析使用
 */
public class MMVInfo {
    /**
     * 系统：iPhone 5，iPad mini , Android phone，华为 pad, 一体机等）
     */
    private String model;

    /**
     * 运行商名称：中国电信、联通、移动，unKnown）
     */
    private String carrier;

    /**
     * 系统版本号：1.0
     */
    private String systemVersion;

    /**
     * @return {@link #model}
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the {@link #model} to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return {@link #carrier}
     */
    public String getCarrier() {
        return carrier;
    }

    /**
     * @param carrier the {@link #carrier} to set
     */
    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    /**
     * @return {@link #systemVersion}
     */
    public String getSystemVersion() {
        return systemVersion;
    }

    /**
     * @param systemVersion the {@link #systemVersion} to set
     */
    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }
}
