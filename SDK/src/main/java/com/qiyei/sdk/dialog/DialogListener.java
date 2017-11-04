package com.qiyei.sdk.dialog;

import android.view.View;

import java.io.Serializable;

/**
 * @author Created by qiyei2015 on 2017/9/15.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface DialogListener extends Serializable{

    /**
     * 点击
     * @param v
     */
    void onClick(View v);

//    /**
//     * 长按
//     * @param v
//     */
//    void onLongClick(View v);

}
