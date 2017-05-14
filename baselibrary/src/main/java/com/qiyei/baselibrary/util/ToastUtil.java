package com.qiyei.baselibrary.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/8.
 * Version: 1.0
 * Description:
 */
public class ToastUtil {

    private static Context mContext;

    public static void init(Context context){
        mContext = context;
    }

    /**
     * 弹出短toast
     * @param msg
     */
    public static void showShortToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出长toast
     * @param msg
     */
    public static void showLongToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
    }

}
