package com.qiyei.baselibrary.view.xrecyclerview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.WrapperListAdapter;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/23.
 * Version: 1.0
 * Description:
 */
public class XRecyclerView extends WrapRecyclerView {
    /**
     * 调试标志
     */
    private static final String TAG = XRecyclerView.class.getSimpleName();
    /**
     * 下拉刷新的辅助类
     */
    private IRefreshViewCreator mRefreshCreator;
    /**
     * 下拉刷新的头部的高度
     */
    private int mRefreshViewHeight = 0;
    /**
     * 下拉刷新的的头部View
     */
    private View mRefreshView;
    /**
     * 手指按下的位置
     */
    private int mFingerDownY = 0;
    /**
     * 手指下拉或者上拉的阻力系数
     */
    private float mDragValue = 0.35f;
    /**
     * 是否在推动
     */
    private boolean isDrag = false;
    /**
     * 当前下拉状态
     */
    private RefrshStatus mRefreshStatus = RefrshStatus.NORMAL;

    /**
     * 下拉刷新和上拉加载更多的监听器
     */
    private XRecyclerViewListener mListener;


    public XRecyclerView(Context context) {
        super(context);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 下载刷新的状态
     */
    public enum RefrshStatus{
        NORMAL,             // 默认状态
        PULL_DOWN,          // 下拉刷新状态
        RELEASE,            // 松开释放状态
        REFRESHING          // 正在刷新状态
    }

    /**
     * 下拉刷新，上拉加载更多的监听器
     */
    public interface XRecyclerViewListener{
        /**
         * 下拉刷新
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        addRefreshView();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            mRefreshViewHeight = mRefreshView.getMeasuredHeight();
            Log.d(TAG,"onLayout,mRefreshViewHeight:" + mRefreshViewHeight);
            if (mRefreshViewHeight > 0){
                setRefreshViewMarginTop(-mRefreshViewHeight);   //隐藏掉RefreshView
            }
        }
    }

    /**
     * 记录手指按下的位置 ,之所以写在dispatchTouchEvent那是因为如果我们处理了条目点击事件，
     * 那么就不会进入onTouchEvent里面，所以只能在这里获取
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mFingerDownY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:         //手指离开时，如果是下拉，就恢复refreshview
                if (isDrag){
                    restoreRefreshView();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_MOVE:
                //如果在最顶部才处理，否则不需要处理，没有达到最顶端，还可以向上滑动也不需要处理
                Log.d(TAG,"onTouchEvent():" + mRefreshStatus);
                if (mRefreshStatus == RefrshStatus.REFRESHING || mRefreshStatus == RefrshStatus.RELEASE){
                    return super.onTouchEvent(e);
                }
                int y = (int)((e.getRawY() - mFingerDownY) * mDragValue);

                //手指下拉
                if (y > 0){
                    y = y - mRefreshViewHeight;
                    Log.d(TAG,"onTouchEvent(MotionEvent ):" + y);
                    setRefreshViewMarginTop(y);
                    updateRefreshView(y);
                    isDrag = true;
                    return false;
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    /**
     * 添加下拉刷新的Creator
     * @param creator
     */
    public void addRefreshViewCreator(IRefreshViewCreator creator){
        mRefreshCreator = creator;
        addRefreshView();
    }

    /**
     * 添加下拉刷新的view
     */
    private void addRefreshView(){
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null && mRefreshCreator != null){
            View header = mRefreshCreator.getRefreshView(getContext(),this);
            if (header != null){
                addHeaderView(header);
                mRefreshView = header;
            }
        }
    }

    /**
     * 设置RefrshView的MarginTop
     * @param marginTop
     */
    private void setRefreshViewMarginTop(int marginTop) {
        MarginLayoutParams params = (MarginLayoutParams) mRefreshView.getLayoutParams();
        if (marginTop < -mRefreshViewHeight){
            marginTop = -mRefreshViewHeight;
        }
        params.topMargin = marginTop;
        Log.d(TAG,"setRefreshViewMarginTop, params.topMargin:" + params.topMargin);
        mRefreshView.setLayoutParams(params);
        //scrollBy(0,marginTop);
    }

    /**
     * 更新refreshView的marginTop
     * @param marginTop
     */
    private void updateRefreshView(int marginTop){

        if (marginTop <= -mRefreshViewHeight){   //正常状态
            mRefreshStatus = RefrshStatus.NORMAL;
        }else if (marginTop < 10 ){    // 下拉状态
            mRefreshStatus = RefrshStatus.PULL_DOWN;
        }else {                       //送卡刷新状态
            mRefreshStatus = RefrshStatus.RELEASE;
        }

        if (mRefreshCreator != null){
            mRefreshCreator.onPull(marginTop,mRefreshViewHeight,mRefreshStatus);
        }
    }

    /**
     * 恢复refreshView
     */
    private void restoreRefreshView() {
        int currentTopMargin = ((MarginLayoutParams)mRefreshView.getLayoutParams()).topMargin;

        int finalTopMargin = -mRefreshViewHeight; //最终顶部会不显示

        //如果是松开刷新状态，没有下拉了
        if (mRefreshStatus == RefrshStatus.RELEASE){
            finalTopMargin = 0;
            //转变成正在刷新状态
            mRefreshStatus = RefrshStatus.REFRESHING;
            if (mRefreshCreator != null){
                mRefreshCreator.onRefreshing();
            }
            if (mListener != null){
                mListener.onRefresh();
            }
        }

        int y = currentTopMargin - finalTopMargin;
        Log.d(TAG,"restoreRefreshView, currentTopMargin:" + currentTopMargin + ",finalTopMargin:" + finalTopMargin);
        //从当前状态恢复到最终状态
        ValueAnimator animator = ObjectAnimator.ofFloat(currentTopMargin,finalTopMargin).setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float dy = (float) animation.getAnimatedValue();
                setRefreshViewMarginTop((int)dy);
            }
        });

        animator.start();
        isDrag = false;
    }

    /**
     * 停止刷新
     */
    public void stopRefrsh(){
        mRefreshStatus = RefrshStatus.NORMAL;
        restoreRefreshView();
        if (mRefreshCreator != null){
            mRefreshCreator.onStopRefreshing();
        }
    }

    /**
     * 判断是不是滚动到了最顶部，这个是从SwipeRefreshLayout里面copy过来的源代码
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */
    public boolean canScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            return ViewCompat.canScrollVertically(this, -1) || this.getScrollY() > 0;
        } else {
            return ViewCompat.canScrollVertically(this, -1);
        }
    }

    /**
     * @param  listener the {@link #mListener} to set
     */
    public void setXRecyclerViewListener(XRecyclerViewListener listener) {
        mListener = listener;
    }
}
