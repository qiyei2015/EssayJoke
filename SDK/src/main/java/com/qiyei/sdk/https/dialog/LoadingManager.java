package com.qiyei.sdk.https.dialog;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * @author Created by qiyei2015 on 2017/10/25.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class LoadingManager {

    /**
     * 显示对话框
     * @param manager
     * @param tag
     */
    public static void showDialog(Object manager, String tag){
        if (manager == null){
            return;
        }
        LoadingDialog dialog = new LoadingDialog();
        dialog.setCancelable(false);
        if (manager instanceof FragmentManager){

            FragmentManager fragmentManager = (FragmentManager) manager;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(dialog, tag);
            fragmentTransaction.commitNowAllowingStateLoss();//立即执行
        }else if (manager instanceof android.app.FragmentManager){
            android.app.FragmentManager fragmentManager = (android.app.FragmentManager) manager;
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(dialog, tag);
//            fragmentTransaction.commitNowAllowingStateLoss();//立即执行
        }
    }

    /**
     * 取消对话框显示
     * @param manager
     * @param tag
     */
    public static void dismissDialog(Object manager,String tag){
        if (manager == null){
            return;
        }
        if (manager instanceof FragmentManager){

            FragmentManager fragmentManager = (FragmentManager) manager;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(dialog, tag);
            fragmentTransaction.commitNowAllowingStateLoss();//立即执行
        }else if (manager instanceof android.app.FragmentManager){
            android.app.FragmentManager fragmentManager = (android.app.FragmentManager) manager;
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.add(dialog, tag);
//            fragmentTransaction.commitNowAllowingStateLoss();//立即执行
        }
        
        LoadingDialog dialog = (LoadingDialog) manager.findFragmentByTag(tag);
        if(dialog != null){
            dialog.dismissAllowingStateLoss();
        }
    }


}
