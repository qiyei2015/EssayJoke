package com.qiyei.sdk.titlebar;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/14.
 * Version: 1.0
 * Description: TitleBar的基本参数
 */
public class BaseTitleParams {
    public Context mContext;
    /**
     * 依附的目标
     */
    public Object mTarget;
    /**
     * 父布局
     */
    public ViewGroup mRootViewGroup;

    public BaseTitleParams(Context context, ViewGroup root){
        mContext = context;
        mRootViewGroup = root;
    }

}
