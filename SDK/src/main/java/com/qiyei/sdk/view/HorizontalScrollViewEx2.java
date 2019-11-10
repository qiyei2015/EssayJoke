package com.qiyei.sdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by qiyei on 2016/5/10.
 */
/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2016/5/10.
 * Version: 1.0
 * Description: 水平滚动的ViewGroup
 */
public class HorizontalScrollViewEx2 extends ViewGroup {

    private static final String TAG = "HorizontalScrollViewEx";

    private int mChildSize;
    private int mChildWidth;
    private int mChildIndex;

    //记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    //记录上次滑动的坐标(onInterceptTouchEvent)
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;

    //弹性滑动
    private Scroller mScroller;
    //速度追踪
    private VelocityTracker mVelocityTracker;

    public HorizontalScrollViewEx2(Context context){
        super(context);
        init();
    }

    public HorizontalScrollViewEx2(Context context, AttributeSet set){
        super(context,set);
        init();
    }

    public HorizontalScrollViewEx2(Context context,AttributeSet set,int defStyle){
        super(context,set,defStyle);
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        mScroller = new Scroller(getContext());
        //没有公有的构造方法，使用其静态方法obtain()方法获得
        mVelocityTracker = VelocityTracker.obtain();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int)ev.getX();
        int y = (int) ev.getY();

        int action = ev.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            mLastX = x;
            mLastY = y;
            if (!mScroller.isFinished()){
                mScroller.abortAnimation();
                return true;
            }
            return false;
        } else {
            return true;
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                scrollBy(-deltaX,0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                int scrollToChildIndex = scrollX / mChildWidth;
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 50){
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1:mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildIndex / 2) / mChildWidth;
                }

                mChildIndex = Math.max(0,Math.min(mChildIndex,mChildSize -1));
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScroollBy(dx,0);
                mVelocityTracker.clear();
                break;
            default:
                break;

        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = 0;
        int measuredHeight = 0;
        int childCount = getChildCount();

        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpaceMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpzceMode = MeasureSpec.getMode(heightMeasureSpec);

        if (childCount == 0){
            setMeasuredDimension(0,0);
        } else if (heightSpzceMode == MeasureSpec.AT_MOST){
            View childView = getChildAt(0);
            measuredHeight = childView.getMeasuredHeight();
            setMeasuredDimension(widthSpaceSize,measuredHeight);
        } else if (widthSpaceMode == MeasureSpec.AT_MOST){
            View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childCount;
            setMeasuredDimension(measuredWidth,heightSpaceSize);
        } else {
            View childView = getChildAt(0);
            measuredWidth = childView.getMeasuredWidth() * childCount;
            measuredHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measuredWidth,measuredHeight);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        int childCount = getChildCount();
        mChildSize = childCount;

        for (int i = 0; i < childCount; i++){
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE){
                int childWidth = childView.getMeasuredWidth();
                mChildWidth = childWidth;
                childView.layout(childLeft,0,childLeft + childWidth
                        ,childView.getMeasuredHeight());

                childLeft += childWidth;

            }
        }
    }

    /**
     * 弹性滑动
     * @param dx x方向滑动dx
     * @param dy y方向滑动dy
     */
    private void smoothScroollBy(int dx,int dy){
        /**
         * 500为滑动时间 ms
         */
        mScroller.startScroll(getScrollX(),0,dx,0,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        //回收速度追踪器
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }

}
