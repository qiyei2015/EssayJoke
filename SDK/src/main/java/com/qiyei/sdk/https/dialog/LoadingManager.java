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
     * @param fragmentManager
     * @param tag
     */
    public static void showDialog(FragmentManager fragmentManager, String tag){
        if (fragmentManager == null){
            return;
        }
        LoadingDialog dialog = new LoadingDialog();
        dialog.setCancelable(false);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(dialog, tag);
        fragmentTransaction.commitNowAllowingStateLoss();//立即执行
    }

    /**
     * 取消对话框显示
     * @param fragmentManager
     * @param tag
     */
    public static void dismissDialog(FragmentManager fragmentManager,String tag){
        if (fragmentManager == null){
            return;
        }
        LoadingDialog dialog = (LoadingDialog) fragmentManager.findFragmentByTag(tag);
        if(dialog != null){
            dialog.dismissAllowingStateLoss();
        }
    }


}
