package com.qiyei.sdk.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.qiyei.sdk.util.ToastUtil;


/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2016/12/23.
 * Version: 1.0
 * Description: 可上下滚动文字的View
 */
public class TestView extends View implements GestureDetector.OnGestureListener{

    private static final String TAG = "TestView";

    private Context mContext;
    private VelocityTracker mVelocityTracker;//速度追踪
    private GestureDetector mGestureDetector;//手势检测
    private Scroller mScroller;//弹性滑动对象

    private Paint mPaint;   //画笔

    private int x;
    private int y;

    public TestView(Context context) {
        this(context,null);
    }

    public TestView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        mVelocityTracker = VelocityTracker.obtain();
        //mGestureDetector = new GestureDetector(mContext,this);
        mScroller = new Scroller(mContext);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);//风格填充

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        Log.d(TAG,"width:" + width + " height:" + height);
        canvas.drawCircle(width / 2,height / 2,width/3,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        mVelocityTracker.computeCurrentVelocity(100);
        Log.d(TAG,"x:" + mVelocityTracker.getXVelocity() + " y:" + mVelocityTracker.getYVelocity());

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)getLayoutParams();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int)event.getRawX() - x;
                int deltaY = (int)event.getRawY() - y;

                params.setMargins(deltaX,deltaY,0,0);
                this.requestLayout();
                x = (int)event.getRawX();
                y = (int)event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
        //return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        //未结束滚动
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 缓慢滚动到指定位置
     * @param x
     * @param y
     */
    private void smoothScrollTo(int x,int y){
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        mScroller.startScroll(scrollX,scrollY,x - scrollX,y - scrollY,500);
        invalidate();//调用onDraw()方法重绘
    }

    /**
     * GestureDetector.OnGestureListener 方法
     */

    @Override
    public boolean onDown(MotionEvent e) {

        ToastUtil.showShortToast("onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mVelocityTracker.addMovement(e1);
        mVelocityTracker.computeCurrentVelocity(100);
        Log.d(TAG,"x:" + mVelocityTracker.getXVelocity() + " y:" + mVelocityTracker.getYVelocity());
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
