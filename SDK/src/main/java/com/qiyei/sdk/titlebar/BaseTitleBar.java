package com.qiyei.sdk.titlebar;

import android.app.Activity;
import android.content.Context;



import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.qiyei.sdk.log.LogManager;


/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/14.
 * Version: 1.0
 * Description:
 */
public abstract class BaseTitleBar<T extends BaseTitleParams> implements ITitleBar {

    protected static final String TAG = BaseTitleBar.class.getSimpleName();

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
        if (mParams.mTarget instanceof Activity){
            bindViewToActivityRoot();
        }else if (mParams.mTarget instanceof Fragment){
            bindViewToFragmentRoot();
        }
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
        ViewGroup activityRoot = null;
        //创建View
        if (mParams.mRootViewGroup == null || activityRoot == null){
            //获取Activity的根布局
            activityRoot = (ViewGroup) ((Activity)mParams.mContext).findViewById(android.R.id.content);
            //获取我们在Activity中设置ContentView的View
            mParams.mRootViewGroup = (ViewGroup)activityRoot.getChildAt(0);
            LogManager.i(TAG,"activityRoot size : " +activityRoot.getChildCount() + " mParams.mRootViewGroup:" + mParams.mRootViewGroup);
        }
        if (mParams.mRootViewGroup == null){
            return;
        }

        mNavigationView = LayoutInflater.from(mParams.mContext)
                .inflate(bindLayoutId(),activityRoot,false);

        if (mParams.mRootViewGroup instanceof RelativeLayout || mParams.mRootViewGroup instanceof ConstraintLayout){
            //相对布局
            //先构造一个线性布局,指定垂直排列
            LinearLayout newRoot = new LinearLayout(mParams.mContext);
            newRoot.setOrientation(LinearLayout.VERTICAL);

            //移除原有的activityRoot的parent，否则会报"The specified child already has a parent. " +
            //"You must call removeView() on the child's parent first. 异常
            ViewGroup viewParent = (ViewGroup)mParams.mRootViewGroup.getParent();
            viewParent.removeAllViews();

            //将titleBar添加为第一个child,原来的activityRoot为第二个
            newRoot.addView(mNavigationView,0);
            newRoot.addView(mParams.mRootViewGroup,1);
            //将新的activityRoot添加到android.R.id.content中
            activityRoot.addView(newRoot,0);

            LogManager.i(TAG,"mParams.mRootViewGroup,child at 0 view:");

        }else if (mParams.mRootViewGroup instanceof CoordinatorLayout){


        } else {
            //添加View到mParams.mParent中
            mParams.mRootViewGroup.addView(mNavigationView,0);
        }
        LogManager.i(TAG,"mParams.mRootViewGroup,child:" + mParams.mRootViewGroup.getChildCount());
        //绑定View
        bindView();
    }


    /**
     * 绑定View到ActivityRoot上
     */
    private void bindViewToFragmentRoot() {
        ViewGroup fragmentRoot = null;
        Fragment fragment = (Fragment) mParams.mTarget;

        //创建View
        if (mParams.mRootViewGroup == null || fragmentRoot == null){
            //获取Fragment的根布局
            fragmentRoot = (ViewGroup) fragment.getView();
            //获取我们在Fragment中设置ContentView的View
            mParams.mRootViewGroup = fragmentRoot;
            LogManager.i(TAG,"fragmentRoot size : " +fragmentRoot.getChildCount() + " mParams.mRootViewGroup:" + mParams.mRootViewGroup);
            if (mParams.mRootViewGroup == null){
                LogManager.i(TAG,"fragment.getView() is null");
                return;
            }
        }
        if (mParams.mRootViewGroup == null){
            return;
        }

        mNavigationView = LayoutInflater.from(mParams.mContext)
                .inflate(bindLayoutId(),fragmentRoot,false);

        if (mParams.mRootViewGroup instanceof RelativeLayout || mParams.mRootViewGroup instanceof ConstraintLayout){
            //相对布局
            //先构造一个线性布局,指定垂直排列
            LinearLayout newRoot = new LinearLayout(mParams.mContext);
            newRoot.setOrientation(LinearLayout.VERTICAL);

            //移除原有的activityRoot的parent，否则会报"The specified child already has a parent. " +
            //"You must call removeView() on the child's parent first. 异常
            ViewGroup viewParent = (ViewGroup)mParams.mRootViewGroup.getParent();
            viewParent.removeAllViews();

            //将titleBar添加为第一个child,原来的activityRoot为第二个
            newRoot.addView(mNavigationView,0);
            newRoot.addView(mParams.mRootViewGroup,1);
            //将新的activityRoot添加到android.R.id.content中
            fragmentRoot.addView(newRoot,0);

            LogManager.i(TAG,"mParams.mRootViewGroup,child at 0 view:");

        }else if (mParams.mRootViewGroup instanceof CoordinatorLayout){

        } else {
            //添加View到mParams.mParent中
            mParams.mRootViewGroup.addView(mNavigationView,0);
        }
        LogManager.i(TAG,"mParams.mRootViewGroup,child:" + mParams.mRootViewGroup.getChildCount());
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
