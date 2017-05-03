package com.qiyei.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/2.
 * Version: 1.0
 * Description: View的findViewById的辅助类
 */
public class ViewFinder {

    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity){
        mActivity = activity;
    }

    public ViewFinder(View view){
        mView = view;
    }

    /**
     * 根据id找到对应的View
     * @param id
     * @return
     */
    public View finderViewById(int id){
        return mActivity != null ? mActivity.findViewById(id) : mView.findViewById(id);
    }

    /**
     * 返回对应的对象
     * @return
     */
    public Object finderObject(){
        if (mActivity != null){
            return mActivity;
        }
        if (mView != null){
            return mView;
        }
        return null;
    }

}
