package com.qiyei.baselibrary.titlebar;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/14.
 * Version: 1.0
 * Description: Navigation的参数
 */
public class AbsTitleParams {
    public Context mContext;
    /**
     * 父布局
     */
    public ViewGroup mParent;

    public AbsTitleParams(Context context, ViewGroup parent){
        mContext = context;
        mParent = parent;
    }

}
