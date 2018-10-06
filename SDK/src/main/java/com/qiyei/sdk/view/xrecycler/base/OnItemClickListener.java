package com.qiyei.sdk.view.xrecycler.base;

import android.view.View;

/**
 * @author Created by qiyei2015 on 2018/1/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface OnItemClickListener<T>{

    /**
     * 点击到item
     * @param view
     * @param item
     * @param position
     */
    void click(View view, T item,int position);

}
