package com.qiyei.sdk.view.xrecycler;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

import com.qiyei.sdk.log.LogManager;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/25.
 * Version: 1.0
 * Description: 下拉刷新，上拉加载的帮助类
 */
public class XRecyclerHelper {
    /**
     * 调试标志
     */
    private static final String TAG = XRecyclerHelper.class.getSimpleName();
    /**
     * 下拉刷新
     */
    public static final int REFRESH = 1;
    /**
     * 上拉加载更多
     */
    public static final int LOAD_MORE = 2;

    /**
     * view的类型
     */
    public int type;
    /**
     * 下拉刷新上拉加载更多的
     */
    public IViewCreator creator;
    /**
     * 下拉刷新上拉加载的view的高度
     */
    public int viewHeight = 0;
    /**
     * 下拉刷新上拉加载的View
     */
    public View view;
    /**
     * 手指按下的y位置
     */
    public int downY = 0;
    /**
     * 手指下拉或者上拉的阻力系数
     */
    public float dragValue = 0.35f;
    /**
     * 是否在下拉或者上拉
     */
    public boolean drag = false;

    /**
     * 当前下拉状态
     */
    public XStatus status = XStatus.NORMAL;


    public XRecyclerHelper(int type) {
        this.type = type;
    }

    /**
     * 添加下拉刷新的Creator
     * @param creator
     */
    public void addViewCreator(IViewCreator creator){
        this.creator = creator;
    }

    /**
     * 设置RefrshView的MarginTop
     * @param topMargin
     */
    public void setViewTopMargin(int topMargin) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.view.getLayoutParams();
        if (topMargin < -this.viewHeight){
            topMargin = -this.viewHeight;
        }
        params.topMargin = topMargin;
        LogManager.d(TAG,"setRefreshViewMarginTop, params.topMargin:" + params.topMargin);
        this.view.setLayoutParams(params);
        this.view.requestLayout();
    }

    /**
     * 恢复并刷新
     * @param listener
     */
    public void restoreViewTopMargin(XRecyclerListener listener) {
        int currentTopMargin = ((ViewGroup.MarginLayoutParams)this.view.getLayoutParams()).topMargin;

        int finalTopMargin = -this.viewHeight; //最终顶部会不显示

        //如果是松开刷新状态，没有下拉了
        if (this.status == XStatus.RELEASE){
            finalTopMargin = 0;
            //转变成正在刷新状态
            this.status = XStatus.RUNNING;
            if (this.creator != null){
                this.creator.onRunning();
            }
            //回调刷新监听器的刷新接口
            if (listener != null){
                listener.onRefresh();
            }
        }

        int y = currentTopMargin - finalTopMargin;
        LogManager.d(TAG,"restoreRefreshView, currentTopMargin:" + currentTopMargin + ",finalTopMargin:" + finalTopMargin);
        //从当前状态恢复到最终状态
        ValueAnimator animator = ObjectAnimator.ofFloat(currentTopMargin,finalTopMargin).setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float dy = (float) animation.getAnimatedValue();
                setViewTopMargin((int)dy);
            }
        });
        animator.start();

        this.drag = false;
    }


    /**
     * 设置RefrshView的MarginTop
     * @param marginBottom
     */
    public void setViewBottomMargin(int marginBottom) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.view.getLayoutParams();
        if (marginBottom < 0){
            marginBottom = 0;
        }
        params.bottomMargin = marginBottom;
        LogManager.d(TAG,"setViewBottomMargin, params.marginBottom:" + params.bottomMargin);
        this.view.setLayoutParams(params);
    }

    /**
     * 恢复并调用listenerde加载方法
     * @param listener
     */
    public void restoreViewBottomMargin(XRecyclerListener listener) {
        int currentBottomMargin = ((ViewGroup.MarginLayoutParams)this.view.getLayoutParams()).bottomMargin;

        int finalBottomMargin = 0; //最终底部会不显示

        //如果是松开，没有上拉加载更多了
        if (this.status == XStatus.RELEASE){
            //转变成正在加载的状态
            this.status = XStatus.RUNNING;
            if (this.creator != null){
                this.creator.onRunning();
            }
            //回调监听器的加载更多接口
            if (listener != null){
                listener.onLoadMore();
            }
        }

        int y = currentBottomMargin - finalBottomMargin;
        LogManager.d(TAG,"restoreViewBottomMargin, currentBottomMargin:" + currentBottomMargin + ",finalBottomMargin:" + finalBottomMargin);
        //从当前状态恢复到最终状态
        ValueAnimator animator = ObjectAnimator.ofFloat(currentBottomMargin,finalBottomMargin).setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float dy = (float) animation.getAnimatedValue();
                setViewBottomMargin(-(int)dy);
            }
        });
        animator.start();

        this.drag = false;
    }

    /**
     * 根据margin更新View的状态
     * @param margin
     */
    public void updateViewStatus(int margin){
        switch (type){
            case REFRESH:
                if (margin <= -this.viewHeight){ //正常状态
                    this.status = XStatus.NORMAL;
                }else if (margin < 0){    // 下拉或者上拉状态
                    this.status = XStatus.PULL;
                }else {                       // 该释放状态
                    this.status = XStatus.RELEASE;
                }
                break;
            case LOAD_MORE:
                if (margin <= 0){ //正常状态
                    this.status = XStatus.NORMAL;
                }else if (margin < this.viewHeight){    // 上拉状态
                    this.status = XStatus.PULL;
                }else {                       // 该释放状态
                    this.status = XStatus.RELEASE;
                }
                break;
            default:
                break;
        }

        if (this.creator != null){
            this.creator.onPull(margin,this.viewHeight,this.status);
        }
    }

}
