package com.qiyei.sdk.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.util.DisplayMetrics;

import com.qiyei.sdk.log.LogUtil;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/8/2.
 * Version: 1.0
 * Description:
 */
public class VersionManager {

    /**
     * 调试用的标志
     */
    private static final String TAG = VersionManager.class.getSimpleName();

    static {
        System.loadLibrary("diffpatch");
    }

    /**
     * 获取当前APK的签名
     * @param context
     * @return
     */
    public static String getSignature(Context context){
        //获取当前应用的包名
        String packageName = context.getApplicationInfo().packageName;
        LogUtil.i(TAG,"getSignature, packageName --> " + packageName);

        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_SIGNATURES);
        for (PackageInfo info : packageInfos){
            //找到与当前应用相同的包名的应用
            if (info.packageName.equals(packageName)){
                LogUtil.i(TAG,"getSignature, signature --> ");
                return info.signatures[0].toCharsString();
            }
        }
        return null;
    }

    /**
     * 获取某个路径下APK的签名
     * @param path
     * @return
     */
    public static String getSignature(String path){
        //反射实例化PackageParser对象
        Object packageParser = getPackageParser(path);
        if (packageParser == null){
            LogUtil.i(TAG,"getSignature, getPackageParser() is null");
            return null;
        }

        //反射获取parserPackage方法
        Object packageObject = getPackageInfo(path,packageParser);
        if (packageObject == null){
            LogUtil.i(TAG,"getSignature, getPackageInfo() is null");
            return null;
        }

        //反射调用collectCertificates方法
        try {
            Method collectCertificatesMethod = packageParser.getClass().getDeclaredMethod("collectCertificates",packageObject.getClass(),int.class);
            if (collectCertificatesMethod != null){
                collectCertificatesMethod.setAccessible(true);
                collectCertificatesMethod.invoke(packageParser,packageObject,0);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        LogUtil.i(TAG,"getSignature, collectCertificatesMethod invoke is ok");
        //获取mSignatures属性
        try {
            Field signatureField = packageObject.getClass().getDeclaredField("mSignatures");
            if (signatureField != null){
                signatureField.setAccessible(true);
                Signature[] signatures = (Signature[]) signatureField.get(packageObject);
                return signatures[0].toCharsString();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        LogUtil.i(TAG,"getSignature, signatureField is error ");
        return null;
    }


    /**
     *
     * @param path
     * @param packageParser
     * @return
     */
    private static Object getPackageInfo(String path,Object packageParser){
        Class<?>[] clazz = null;
        Method parserPackageMethod = null;
        Object[] objects = null;

        //大于API 21 需要单独处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            clazz = new Class[2];
            clazz[0] = File.class;
            clazz[1] = int.class;

            objects = new Object[2];
            objects[0] = new File(path);
            objects[1] = 0;

        }else {
            clazz = new Class[4];
            clazz[0] = File.class;
            clazz[1] = String.class;
            clazz[2] = DisplayMetrics.class;
            clazz[3] = int.class;

            objects = new Object[4];
            objects[0] = new File(path);
            objects[1] = path;
            DisplayMetrics metrics = new DisplayMetrics();
            metrics.setToDefaults();
            objects[2] = metrics;
            objects[3] = 0;
        }

        try {
            parserPackageMethod = packageParser.getClass().getDeclaredMethod("parsePackage",clazz);
            //反射执行该parsePackage方法
            if (parserPackageMethod != null){
                parserPackageMethod.setAccessible(true);
                return parserPackageMethod.invoke(packageParser,objects);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取对应路径的APK的PackageParser
     * @param path
     * @return
     * @throws Exception
     */
    private static Object getPackageParser(String path){
        try {
            Class<?> packageParserClazz = Class.forName("android.content.pm.PackageParser");
            Constructor<?> packageParserConstructor = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                packageParserConstructor = packageParserClazz.getDeclaredConstructor();
                return packageParserConstructor.newInstance();
            }else {
                packageParserConstructor = packageParserClazz.getDeclaredConstructor(String.class);
                return packageParserConstructor.newInstance(path);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param oldApkPath 原来的apk  1.0 本地安装的apk
     * @param newApkPath 合并后新的apk路径   需要生成的2.0
     * @param patchPath 差分包路径， 从服务器上下载下来
     */
    public static native void versionCombine(String oldApkPath,String newApkPath,String patchPath);
}
