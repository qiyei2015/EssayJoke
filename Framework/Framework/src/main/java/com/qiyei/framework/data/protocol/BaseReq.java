package com.qiyei.framework.data.protocol;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Created by qiyei2015 on 2017/10/25.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class BaseReq implements Serializable{

    @SerializedName("app_name")
    private String appName;

    @SerializedName("version_name")
    private String versionName;

    @SerializedName("ac")
    private String ac;

    @SerializedName("device_id")
    private String deviceId;

    @SerializedName("device_brand")
    private String deviceBrand;

    @SerializedName("update_version_code")
    private String updateVersionCode;

    @SerializedName("manifest_version_code")
    private String manifestVersionCode;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("device_platform")
    private String devicePlatform;

    public BaseReq() {
        appName = "joke_essay";
        versionName = "5.7.0";
        ac = "wifi";
        deviceId = "30036118478";
        deviceBrand = "Xiaomi";
        updateVersionCode = "5701";
        manifestVersionCode = "570";
        longitude = "113.000366";
        latitude = "28.171377";
        devicePlatform = "android";
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
     * @return {@link #ac}
     */
    public String getAc() {
        return ac;
    }

    /**
     * @param ac the {@link #ac} to set
     */
    public void setAc(String ac) {
        this.ac = ac;
    }

    /**
     * @return {@link #deviceId}
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the {@link #deviceId} to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return {@link #deviceBrand}
     */
    public String getDeviceBrand() {
        return deviceBrand;
    }

    /**
     * @param deviceBrand the {@link #deviceBrand} to set
     */
    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    /**
     * @return {@link #updateVersionCode}
     */
    public String getUpdateVersionCode() {
        return updateVersionCode;
    }

    /**
     * @param updateVersionCode the {@link #updateVersionCode} to set
     */
    public void setUpdateVersionCode(String updateVersionCode) {
        this.updateVersionCode = updateVersionCode;
    }

    /**
     * @return {@link #manifestVersionCode}
     */
    public String getManifestVersionCode() {
        return manifestVersionCode;
    }

    /**
     * @param manifestVersionCode the {@link #manifestVersionCode} to set
     */
    public void setManifestVersionCode(String manifestVersionCode) {
        this.manifestVersionCode = manifestVersionCode;
    }

    /**
     * @return {@link #longitude}
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the {@link #longitude} to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return {@link #latitude}
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the {@link #latitude} to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return {@link #devicePlatform}
     */
    public String getDevicePlatform() {
        return devicePlatform;
    }

    /**
     * @param devicePlatform the {@link #devicePlatform} to set
     */
    public void setDevicePlatform(String devicePlatform) {
        this.devicePlatform = devicePlatform;
    }
}
