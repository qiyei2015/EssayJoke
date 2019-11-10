package com.qiyei.sdk.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.qiyei.sdk.R;


/**
 * Created by hanxw on 2016/8/2.
 */
/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2016/8/2.
 * Version: 1.0
 * Description: 字体会逐步变色的TextView，其思想就是两个颜色字体分别画
 */
public class CustomCircleView extends View{

    private Context mContext;
    private boolean threadRun;//线程运行标志
    private int mFirstColor;//第一种颜色
    private int mSecondColor;//第二种颜色
    private int mSpeed;//绘制的速度
    private int mCircleWidth;//圆环的宽度
    private int mProgress;//当前进度
    private boolean isNext = false;//是否进行下一次绘制

    private Paint mPaint;//画笔

    public CustomCircleView(Context context){
        this(context,null);
    }

    public CustomCircleView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public CustomCircleView(Context context,AttributeSet attrs,int defstyle){
        super(context,attrs,defstyle);
        mContext = context;

        threadRun = true;//线程运行

        //获取xml属性
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.CustomCircleView);
        //默认黑色
        mFirstColor = array.getColor(R.styleable.CustomCircleView_firstColor, Color.BLACK);
        //默认绿色
        mSecondColor = array.getColor(R.styleable.CustomCircleView_secondColor,Color.GREEN);
        //默认20dp
        mCircleWidth = (int)array.getDimension(R.styleable.CustomCircleView_circleWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,20,getResources().getDisplayMetrics()));
        mSpeed = array.getInteger(R.styleable.CustomCircleView_speed,10);

        //回收TypeArray
        array.recycle();

        mPaint = new Paint();
        //设置抗锯齿
        mPaint.setAntiAlias(true);

        //开启一个线程来改变progress的值
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (threadRun) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        //将isNext反置
                        if (!isNext) {
                            isNext = true;
                        } else {
                            isNext = false;
                        }
                    }

                    //重新绘制
                    postInvalidate();

                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //获取圆心
        int center = getWidth() / 2;
        //半径
        int radius = center - mCircleWidth / 2;
        //设置圆环宽度
        mPaint.setStrokeWidth(mCircleWidth);
        //设置空心
        mPaint.setStyle(Paint.Style.STROKE);
        //定义圆弧的形状和大小的界限
        RectF rectF = new RectF(center - radius,center - radius,center + radius,center + radius);

        if (!isNext){
            //第一圈的颜色完整，第二圈开始跑
            //设置圆环颜色
            mPaint.setColor(mFirstColor);
            //画圆
            canvas.drawCircle(center,center,radius,mPaint);

            //设置第二圈颜色
            mPaint.setColor(mSecondColor);
            //根据进度画圆弧
            canvas.drawArc(rectF,0,mProgress,false,mPaint);
        } else {
            //第二圈的颜色完整，第一圈开始跑
            //设置圆环颜色
            mPaint.setColor(mSecondColor);
            //画圆
            canvas.drawCircle(center,center,radius,mPaint);
            //设置第一圈颜色
            mPaint.setColor(mFirstColor);
            //根据进度画圆
            canvas.drawArc(rectF,0,mProgress,false,mPaint);
        }
    }

    /**
     * 包含此View的Activity退出时会调用
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        //停止后台线程
        threadRun = false;
    }
}
