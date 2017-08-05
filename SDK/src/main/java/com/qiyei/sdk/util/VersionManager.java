package com.qiyei.sdk.util;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/2.
 * Version: 1.0
 * Description:
 */
public class VersionManager {


//    static {
//        System.loadLibrary("diffpatch");
//    }

    /**
     *
     * @param oldApkPath 原来的apk  1.0 本地安装的apk
     * @param newApkPath 合并后新的apk路径   需要生成的2.0
     * @param patchPath 差分包路径， 从服务器上下载下来
     */
    public static native void versionCombine(String oldApkPath,String newApkPath,String patchPath);
}
