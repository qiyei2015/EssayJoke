package com.qiyei.framework.titlebar;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.qiyei.sdk.titlebar.BaseTitleParams;

/**
 * @author : 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/14.
 * Version: 1.0
 * Description: 定义该NaviagtionBar支持的所有效果，包括主标题，右标题等
 */
public class TabLayoutTitleParams extends BaseTitleParams {
    /**
     * 关联的ViewPager
     */
    ViewPager mViewPager;
    /**
     * tabMode
     */
    int tabMode = TabLayout.MODE_SCROLLABLE;
    /**
     * 标题右侧文字
     */
    String mRightText;
    /**
     * 右侧点击事件
     */
    View.OnClickListener mRightClickListener;
    /**
     * 左侧View是否显示，默认显示
     */
    boolean isBackViewVisible = true;

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

    public TabLayoutTitleParams(Context context, ViewGroup parent) {
        super(context, parent);
    }

}
