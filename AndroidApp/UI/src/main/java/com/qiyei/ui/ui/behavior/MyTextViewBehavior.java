package com.qiyei.ui.ui.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * @author Created by qiyei2015 on 2020/1/4.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class MyTextViewBehavior extends CoordinatorLayout.Behavior {

    public MyTextViewBehavior() {
        super();
    }

    public MyTextViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 根据条件过滤判断返回值，返回true联动，返回flase不联动，即behavior不生效
     * @param parent 当前的CoordinatorLayout
     * @param child 设置Behavior 的View
     * @param dependency 我们关心的那个View
     * @return
     */
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return true;
    }

    /**
     * 根据我们关心的View的变化来对我们设置Behavior的View进行一系列处理
     * @param parent 当前的CoordinatorLayout
     * @param child 设置Behavior 的View
     * @param dependency 我们关心的那个View
     * @return
     */
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {

        int top = dependency.getTop();
        int left = dependency.getLeft();

        //根据dependency改变其child布局
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        params.topMargin = top - 200;
        params.leftMargin = left + 50;

        return true;
    }
}
