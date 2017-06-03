package com.qiyei.sdk.view.IndicatorView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/17.
 * Version: 1.0
 * Description: Indicator的容器View，包含itemView 与底部的跟踪指示器
 */
public class IndicatorGroupView extends FrameLayout {
    private static final String TAG = IndicatorGroupView.class.getSimpleName();
    /**
     * itemView容器
     */
    private LinearLayout mIndicatorContainer;
    /**
     * 底部的跟踪指示器View
     */
    private View mBottomTrackView;
    /**
     * 一个条目的宽度
     */
    private int mItemWidth;
    /**
     * 底部的布局参数
     */
    private LayoutParams mTrackParams;
    /**
     * 初始的左部分的margin
     */
    private int mInitLeftMargin;

    public IndicatorGroupView(Context context) {
        this(context,null);
    }

    public IndicatorGroupView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IndicatorGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mIndicatorContainer = new LinearLayout(context);
        addView(mIndicatorContainer);
    }

    /**
     * 添加itemView
     * @param itemView
     */
    public void addItemView(View itemView){
        mIndicatorContainer.addView(itemView);
    }

    /**
     * 获取position处的view
     * @param position
     * @return
     */
    public View getItemView(int position){
        return mIndicatorContainer.getChildAt(position);
    }

    /**
     * 添加底部的指示器
     * @param bottomTrackView
     * @param itemWidth
     */
    public void addBottomTrackView(View bottomTrackView,int itemWidth){
        if (bottomTrackView == null){
            return;
        }
        Log.d(TAG,"mBottomTrackView: itemWidth:" + itemWidth);

        mBottomTrackView = bottomTrackView;
        mItemWidth = itemWidth;

        //要让它的宽度 -> 一个条目的宽度,并且让其在底部
        mTrackParams = (LayoutParams)mBottomTrackView.getLayoutParams();
        mTrackParams.gravity = Gravity.BOTTOM;
        //由于是FrameLayout需要指定topMargin，否则会重叠在一起
        mTrackParams.topMargin = mIndicatorContainer.getMeasuredHeight() + 10;

        //获得第一个添加的View
        int trackWidth = mTrackParams.width;

        //没有设置宽度
        if (trackWidth == ViewGroup.LayoutParams.MATCH_PARENT){
            trackWidth = mItemWidth;
        }

        //宽度过大
        if (trackWidth > mItemWidth){
            trackWidth = mItemWidth;
        }

        mTrackParams.width = trackWidth;
        mInitLeftMargin = (mItemWidth - trackWidth) / 2;
        mTrackParams.leftMargin = mInitLeftMargin;

        mBottomTrackView.setLayoutParams(mTrackParams);
        Log.d(TAG,"mBottomTrackView: width:" + mBottomTrackView.getWidth() + "," + mBottomTrackView.getHeight() + " "+ mBottomTrackView.getBackground());
        addView(mBottomTrackView);
    }

    /**
     * smoothScrool滚动底部跟踪器
     * @param position
     * @param offset
     */
    public void scrollBottomTrack(int position, float offset){
        if (mBottomTrackView == null){
            return;
        }

        int leftMargin = (int) ((position + offset) * mItemWidth);

        //控制leftMargin去移动
        mTrackParams.leftMargin = leftMargin + mInitLeftMargin;
        mBottomTrackView.setLayoutParams(mTrackParams);
        //requestLayout();
    }

    /**
     * 滚动底部的指示器 点击移动带动画 -> leftMargin
     * @param position
     */
    public void scrollBottomTrack(int position){
        if (mBottomTrackView == null){
            return;
        }

        //最终要移动到的位置
        int finalLeftMargin = (int)(position * mItemWidth + mInitLeftMargin);
        //当前的位置
        int currentLeftMargin = mTrackParams.leftMargin;
        //移动的距离
        int offset = finalLeftMargin - currentLeftMargin;

        //使用属性动画去移动
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(currentLeftMargin,finalLeftMargin)
                .setDuration((long)(Math.abs(offset) * 0.3f));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float current = (Float) animation.getAnimatedValue();
                mTrackParams.leftMargin = (int) current;
                mBottomTrackView.setLayoutParams(mTrackParams);

            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
    }

}
