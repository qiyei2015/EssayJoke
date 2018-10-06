package com.qiyei.framework.titlebar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.qiyei.sdk.titlebar.BaseTitleParams;

/**
 * @author : 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/14.
 * Version: 1.0
 * Description: 定义该NaviagtionBar支持的所有效果，包括主标题，右标题等
 */
public class CommonTitleParams extends BaseTitleParams {
    /**
     * 主标题
     */
    public String mTitle;
    /**
     * 标题右侧文字
     */
    public String mRightText;
    /**
     * 右侧点击事件
     */
    public View.OnClickListener mRightClickListener;
    /**
     * 左侧View是否显示，默认显示
     */
    public boolean isBackViewVisible = true;

    /**
     * 左侧点击事件
     */
    public View.OnClickListener mLeftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 关闭当前Activity
            ((Activity) mContext).finish();
        }
    };

    public CommonTitleParams(Context context, ViewGroup parent) {
        super(context, parent);
    }

}
