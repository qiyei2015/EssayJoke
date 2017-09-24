package com.qiyei.sdk.titlebar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiyei.sdk.log.LogManager;


/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/14.
 * Version: 1.0
 * Description:
 */
public abstract class BaseTitleBar<T extends BaseTitleParams> implements ITitleBar {
    /**
     * 参数
     */
    protected T mParams;
    /**
     * 导航View
     */
    protected View mNavigationView;

    public BaseTitleBar(T params) {
        this.mParams = params;
        bindViewToActivityRoot();
    }

    /**
     * 设置文本
     * @param viewId
     * @param text
     */
    protected void setText(int viewId,CharSequence text){
        TextView tv = getView(viewId);
        if (!TextUtils.isEmpty(text) && tv != null){
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    /**
     * 设置图片
     * @param viewId
     * @param resId
     */
    protected void setDrawable(int viewId,int resId){
        ImageView imv = getView(viewId);
        if (imv != null){
            imv.setVisibility(View.VISIBLE);
            imv.setImageResource(resId);
        }
    }

    /**
     * 设置点击事件
     * @param viewId
     * @param listener
     */
    protected void setOnClickListener(int viewId,View.OnClickListener listener){
        View view = getView(viewId);
        if (view != null){
            view.setOnClickListener(listener);
        }
    }

    /**
     * 获取view
     * @param viewId
     * @param <V>
     * @return
     */
    public <V extends View> V getView(int viewId){
        return (V) mNavigationView.findViewById(viewId);
    }

    /**
     * 绑定View到ActivityRoot上
     */
    private void bindViewToActivityRoot() {
        //创建View
        if (mParams.mActivityRoot == null){
            //获取Activity的根布局
            ViewGroup activityRoot = (ViewGroup) ((Activity)mParams.mContext).findViewById(android.R.id.content);
            //获取我们在Activity中设置ContentView的View
            mParams.mActivityRoot = (ViewGroup)activityRoot.getChildAt(0);
            LogManager.i("TAG","mParams.mParent:" + mParams.mActivityRoot);
        }
        if (mParams.mActivityRoot == null){
            return;
        }

        mNavigationView = LayoutInflater.from(mParams.mContext)
                .inflate(bindLayoutId(),mParams.mActivityRoot,false);

        //添加View到mParams.mParent中
        mParams.mActivityRoot.addView(mNavigationView,0);

        //绑定View
        bindView();
    }

    /**
     * Builder设计模式,主要用于设置各种效果
     */
    public static abstract class Builder{

        public Builder(Context context) {

        }

        public abstract BaseTitleBar build();
    }

}
