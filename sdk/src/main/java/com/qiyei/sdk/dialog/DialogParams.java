package com.qiyei.sdk.dialog;

import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/13.
 * Version: 1.0
 * Description: Dialog的控制类，控制其中的ContentView等的操作
 */
public class DialogParams implements Serializable{

    /**
     * Dialog辅助类
     */
    public DialogViewHolder mHolder;

    /**
     * 显示DialogFragment需要的FragmentManager
     */
    public FragmentManager mFragmentManager;
    /**
     * 宽度
     */
    public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
    /**
     * 动画
     */
    public int mAnimations = 0;
    /**
     * 位置
     */
    public int mGravity = Gravity.CENTER;
    /**
     * 高度
     */
    public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
    /**
     * 内容View
     */
    public View mContentView;
    /**
     * 内容布局id
     */
    public int mLayoutId;
    /**
     * 是否可以取消
     */
    public boolean isCancelable;

    /**
     * 记录事件的map,这里的事件包括setText，D监听器，图片等
     */
    public Map<String,HashMap<Integer,Object>> mEventMap = new HashMap<>();


    @Override
    public String toString() {
        return "DialogParams{" +
                "mHolder=" + mHolder +
                ", mFragmentManager=" + mFragmentManager +
                ", mWidth=" + mWidth +
                ", mAnimations=" + mAnimations +
                ", mGravity=" + mGravity +
                ", mHeight=" + mHeight +
                ", mContentView=" + mContentView +
                ", mLayoutId=" + mLayoutId +
                ", isCancelable=" + isCancelable +
                '}';
    }
}
