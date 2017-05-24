package com.qiyei.framework.titlebar;

import android.content.Context;
import android.view.View;

import com.qiyei.baselibrary.titlebar.AbsTitleBar;
import com.qiyei.framework.R;


/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/14.
 * Version: 1.0
 * Description:
 */
public class CommonTitleBar extends AbsTitleBar<CommonTitleParams> {

    public CommonTitleBar(CommonTitleParams params) {
        super(params);
    }

    /**
     * 布局文件
     * @return
     */
    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {
        setText(R.id.title,mParams.mTitle);
        setText(R.id.right_text,mParams.mRightText);

        setOnClickListener(R.id.right_text,mParams.mRightClickListener);
        // 左边 要写一个默认的  finishActivity
        setOnClickListener(R.id.back,mParams.mLeftClickListener);
    }

    /**
     * Builder模式设置各种效果
     */
    public static class Builder extends AbsTitleBar.Builder{
        /**
         * 所有的效果参数
         */
        CommonTitleParams mBarParams;

        public Builder(Context context) {
            super(context);
            mBarParams = new CommonTitleParams(context,null);
        }

        /**
         * 设置Title
         * @param title
         * @return
         */
        public Builder setTitle(String title){
            mBarParams.mTitle = title;
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
         * 创建CommonNavigationBar
         * @return
         */
        @Override
        public CommonTitleBar build() {
            return new CommonTitleBar(mBarParams);
        }
    }

}
