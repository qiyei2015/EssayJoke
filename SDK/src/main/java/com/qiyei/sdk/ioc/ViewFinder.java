package com.qiyei.sdk.ioc;

import android.app.Activity;

import android.view.View;

import androidx.fragment.app.Fragment;


/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/2.
 * Version: 1.0
 * Description: View的findViewById的辅助类
 */
public class ViewFinder {
    /**
     * 所保存的activity实例
     */
    private Activity mActivity;
    /**
     * 所保存的Fragment对象
     */
    private Fragment mFragment;
    /**
     * View实例
     */
    private View mView;
    /**
     * class实例
     */
    private Class<?> mClass;

    public ViewFinder(Activity activity){
        mActivity = activity;
        mClass = activity.getClass();
    }

    public ViewFinder(View view){
        mView = view;
        mClass = view.getClass();
    }

    public ViewFinder(Fragment fragment, View view){
        mView = view;
        mFragment = fragment;
        mClass = fragment.getClass();
    }

    /**
     * 根据id找到对应的View
     * @param id
     * @return
     */
    public View finderViewById(int id){
        if (mActivity != null){
            return mActivity.findViewById(id);
        }
        if (mFragment != null){
            return mView.findViewById(id);
        }
        if (mView != null){
            return mView.findViewById(id);
        }
        return null;
    }

    /**
     * 返回对应的对象
     * @return
     */
    public Object finderObject(){
        if (mActivity != null){
            return mActivity;
        }
        //必须把fragment写在view前面
        if (mFragment != null){
            return mFragment;
        }
        if (mView != null){
            return mView;
        }
        return null;
    }

    /**
     * 返回对应的Class实例
     * @return
     */
    public Class<?> findClass(){
        return mClass;
    }
}
