package com.qiyei.sdk.permission;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.qiyei.sdk.log.LogManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/9/23.
 * Version: 1.0
 * Description: 权限管理器 Android6.0以后采用的是运行时权限，因此需要单独处理
 */
public class PermissionManager {

    /**
     * 权限申请对象
     */
    private Object mObject;

    /**
     * 申请权限时的code
     */
    private int mRequestCode;
    /**
     * 需要申请权限的权限数据
     */
    private String[] mPermissions;

    /**
     * 构造方法私有化
     */
    private PermissionManager(Object object){
        mObject = object;
    }

    /**
     * 构造方法私有化
     */
    private PermissionManager(Object object,int code,String[] permissions){
        this(object);
        mRequestCode = code;
        mPermissions = permissions;
    }

    /**
     * 申请权限
     * @param activity
     * @param requestCode
     * @param permissions
     */
    public static void requestPermission(Activity activity , int requestCode,String[] permissions){
        PermissionManager manager = new PermissionManager(activity,requestCode,permissions);
        manager.request();

    }

    /**
     * 申请权限
     * @param fragment
     * @param requestCode
     * @param permissions
     */
    public static void requestPermission(Fragment fragment , int requestCode,String[] permissions){
        PermissionManager manager = new PermissionManager(fragment,requestCode,permissions);
        manager.request();
    }

    /**
     * 申请权限
     * @param fragment
     * @param requestCode
     * @param permissions
     */
    public static void requestPermission(android.app.Fragment fragment , int requestCode,String[] permissions){
        PermissionManager manager = new PermissionManager(fragment,requestCode,permissions);
        manager.request();
    }


    /**
     * 申请所有的危险权限
     */
    public static void requestAllDangerousPermission(Activity object){
        PermissionManager manager = new PermissionManager(object);
        manager.mRequestCode = 345;

        String[] permissions = new String[]{PermissionConstant.group_CALENDAR,PermissionConstant.group_CAMERA
                ,PermissionConstant.group_CONTACTS,PermissionConstant.group_LOCATION,PermissionConstant.group_MICROPHONE
                ,PermissionConstant.group_PHONE,PermissionConstant.group_SENSORS,PermissionConstant.group_SMS
                ,PermissionConstant.group_STORAGE};
        manager.mPermissions = permissions;
        manager.request();
    }

    /**
     * 权限申请后的回调
     * @param object
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResult(Object object,int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        String[] noApplyPermissions = getNoApplyPermissions(object,permissions);
        if (noApplyPermissions == null){
            LogManager.e(PermissionConstant.TAG,"onRequestPermissionsResult(),noApplyPermissions is null");
            return;
        }

        if (noApplyPermissions.length == 0){
            //无没有申请的权限
            PermissionProcessor.requestPermissionSuccess(object,requestCode);
        }else {
            //申请权限
            ActivityCompat.requestPermissions(getActivityFormObject(object),noApplyPermissions,requestCode);
        }
    }

    /**
     * 申请权限
     */
    private void request(){
        //小于6.0直接执行permissionSuceess
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            PermissionProcessor.requestPermissionSuccess(mObject,mRequestCode);
            LogManager.i(PermissionConstant.TAG,"request(),Build.VERSION.SDK_INT is:" + Build.VERSION.SDK_INT);
            return;
        }

        String[] noApplyPermissions = getNoApplyPermissions(mObject,mPermissions);
        if (noApplyPermissions == null){
            LogManager.e(PermissionConstant.TAG,"request(),noApplyPermissions is null");
            return;
        }

        if (noApplyPermissions.length == 0){
            //无没有申请的权限
            PermissionProcessor.requestPermissionSuccess(mObject,mRequestCode);
        }else {
            //申请权限
            ActivityCompat.requestPermissions(getActivityFormObject(mObject),noApplyPermissions,mRequestCode);
        }
    }

    /**
     * 获取权限中被拒绝的
     * @param object
     * @param permissions
     * @return
     */
    private static String[] getNoApplyPermissions(Object object,String[] permissions){
        List<String> permissionList = new ArrayList<>();
        Activity activity = getActivityFormObject(object);
        if (activity == null){
            LogManager.e(PermissionConstant.TAG,"getNotApplyPermissions(),activity is null");
            return null;
        }
        for (String s : permissions){
            if (ContextCompat.checkSelfPermission(activity, s) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(s);
            }
        }
        return permissionList.toArray(new String[]{});
    }

    /**
     * 获取从object中的Activity,主要从Activity，android.support.v4.app.Fragmen，Fragment等中获取
     * @param object
     * @return
     */
    private static Activity getActivityFormObject(Object object){
        Activity activity = null;
        if (object instanceof Activity){
            activity = (Activity)object;
        }else if (object instanceof Fragment){
            activity = ((Fragment)object).getActivity();
        }else if (object instanceof android.app.Fragment){
            activity = ((android.app.Fragment)object).getActivity();
        }
        LogManager.i(PermissionConstant.TAG,"getActivityFormObject(),activity is :" + activity);
        return activity;
    }




}
