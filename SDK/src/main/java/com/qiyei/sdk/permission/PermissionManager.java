package com.qiyei.sdk.permission;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/9/23.
 * Version: 1.0
 * Description: 权限管理器 Android6.0以后采用的是运行时权限，因此需要单独处理
 */
public class PermissionManager {

    /**
     * 申请权限的Activity
     */
    private Activity mActivity;
    /**
     * 申请权限的Fragment
     */
    private Fragment mFragment;
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
    private PermissionManager(){

    }

    /**
     * 申请权限
     * @param activity
     * @param requestCode
     * @param permissions
     */
    public static void requestPermission(Activity activity , int requestCode,String[] permissions){
        PermissionManager manager = new PermissionManager();
        manager.mActivity = activity;
        manager.mRequestCode = requestCode;
        manager.mPermissions = permissions;
        manager.request();

    }

    /**
     * 申请权限
     * @param fragment
     * @param requestCode
     * @param permissions
     */
    public static void requestPermission(Fragment fragment , int requestCode,String[] permissions){
        PermissionManager manager = new PermissionManager();
        manager.mFragment = fragment;
        manager.mRequestCode = requestCode;
        manager.mPermissions = permissions;
        manager.request();
    }

    /**
     * 申请所有的危险权限
     */
    public static void requestAllDangerousPermission(Activity activity){
        PermissionManager manager = new PermissionManager();
        manager.mActivity = activity;
        manager.mRequestCode = 345;

        String[] permissions = new String[]{PermissionConstant.group_CALENDAR,PermissionConstant.group_CAMERA
                ,PermissionConstant.group_CONTACTS,PermissionConstant.group_LOCATION,PermissionConstant.group_MICROPHONE
                ,PermissionConstant.group_PHONE,PermissionConstant.group_SENSORS,PermissionConstant.group_SMS
                ,PermissionConstant.group_STORAGE};
        manager.mPermissions = permissions;
        manager.request();
    }

    /**
     * 申请权限
     */
    private void request(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }

        //确保满足二者之一
        if (mPermissions != null && mActivity!= null){
            ActivityCompat.requestPermissions(mActivity,mPermissions,mRequestCode);
        }
        if (mPermissions != null && mFragment!= null){
            ActivityCompat.requestPermissions(mFragment.getActivity(),mPermissions,mRequestCode);
        }
    }


}
