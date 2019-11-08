package com.qiyei.framework.titlebar;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.qiyei.framework.R;
import com.qiyei.sdk.titlebar.BaseTitleBar;


/**
 * @author: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/14.
 * Version: 1.0
 * Description:
 */
public class TabLayoutTitleBar extends BaseTitleBar<TabLayoutTitleParams> {

    protected TabLayoutTitleBar(TabLayoutTitleParams params) {
        super(params);
    }

    /**
     * 布局文件
     * @return
     */
    @Override
    public int bindLayoutId() {
        return R.layout.tab_layout_title_bar;
    }

    @Override
    public void bindView() {
        TabLayout tabLayout = getView(R.id.tab_layout);
        tabLayout.setupWithViewPager(mParams.mViewPager);
        tabLayout.setTabMode(mParams.tabMode);
        setText(R.id.right_text,mParams.mRightText);
        setOnClickListener(R.id.right_text,mParams.mRightClickListener);
        // 左边 要写一个默认的  finishActivity
        setOnClickListener(R.id.back,mParams.mLeftClickListener);
        if (!mParams.isBackViewVisible){
            getView(R.id.back).setVisibility(View.GONE);
        }
    }

    /**
     * 设置右侧文字
     * @param text
     * @return
     */
    public void setRightText(String text){
        mParams.mRightText = text;
        setText(R.id.right_text,mParams.mRightText);
    }

    /**
     * Builder模式设置各种效果
     */
    public static class Builder extends BaseTitleBar.Builder{
        /**
         * 所有的效果参数
         */
        TabLayoutTitleParams mBarParams;

        public Builder(Activity activity) {
            super(activity);
            mBarParams = new TabLayoutTitleParams(activity,null);
            mBarParams.mTarget = activity;
        }

        public Builder(Fragment fragment) {
            super(fragment.getContext());
            mBarParams = new TabLayoutTitleParams(fragment.getContext(),null);
            mBarParams.mTarget = fragment;
        }

        /**
         * 设置ViewPager
         * @param viewPager
         */
        public Builder setupWithViewPager(@Nullable ViewPager viewPager) {
            mBarParams.mViewPager = viewPager;
            return this;
        }

        /**
         * 设置tablayout的mode
         * @param mode
         */
        public Builder setTabMode(int mode) {
            mBarParams.tabMode = mode;
            return this;
        }

        /**
         * 设置右侧文字
         * @param text
         * @return
         */
        public Builder setRightText(String text){
            mBarParams.mRightText = text;
            return this;
        }

        /**
         * 设置右侧点击事件
         * @param listener
         * @return
         */
        public Builder setRightClickListener(View.OnClickListener listener){
            mBarParams.mRightClickListener = listener;
            return this;
        }

        /**
         * 设置右侧点击事件
         * @param listener
         * @return
         */
        public Builder setLeftClickListener(View.OnClickListener listener){
            mBarParams.mLeftClickListener = listener;
            return this;
        }

        /**
         * 设置左侧View是否显示
         * @param visible
         * @return
         */
        public Builder setLeftViewVisible(boolean visible){
            mBarParams.isBackViewVisible = visible;
            return this;
        }

        /**
         * 创建CommonNavigationBar
         * @return
         */
        @Override
        public TabLayoutTitleBar build() {
            return new TabLayoutTitleBar(mBarParams);
        }
    }

}
