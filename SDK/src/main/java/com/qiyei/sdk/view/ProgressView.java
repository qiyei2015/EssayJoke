package com.qiyei.sdk.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.qiyei.sdk.R;
import com.qiyei.sdk.util.DisplayUtil;

/**
 * @author Created by qiyei2015 on 2017/11/4.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class ProgressView extends View {

    /**
     * Context
     */
    private Context mContext;

    /**
     * 底部颜色
     */
    private int mBottomColor;
    /**
     * 顶部颜色
     */
    private int mTopColor;
    /**
     * 圆环的宽度
     */
    private int mRingWidth;
    /**
     * 当前进度
     */
    private int mProgress;
    /**
     * 中间文字字体颜色
     */
    private int mTextColor;
    /**
     * 中间文字字体大小
     */
    private int mTextSize;

    /**
     * 画笔
     */
    private Paint mPaint;


    public ProgressView(Context context) {
        this(context,null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //这里的是随便指定的，正常情况下需要另加考虑
        int width = DisplayUtil.dip2px(mContext,80);
        int height = DisplayUtil.dip2px(mContext,80);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(width,height);
        } else if (widthSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(width,heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,height);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //圆心在中心
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        //半径为 centerX - 环宽度的一半
        int radius = centerX - mRingWidth / 2;

        //先画底部的圆环，设置style为圆环
        mPaint.setColor(mBottomColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mRingWidth);

        canvas.drawCircle(centerX,centerY,radius,mPaint);

        //定义圆弧的形状和大小的界限
        RectF rectRing = new RectF(centerX - radius,centerY - radius,centerX + radius,centerY + radius);

        //画顶部的圆环
        mPaint.setColor(mTopColor);
        int angle = mProgress * 360 / 100;
        canvas.drawArc(rectRing,270,angle,false,mPaint);

        //画文字
        String text = mProgress + "%";
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setStrokeWidth(0);
        Rect rect = new Rect();
        //得到字体的Bound宽高
        mPaint.getTextBounds(text,0,text.length(),rect);
        //x - bound/2
        int textX = centerX - (rect.width() / 2);
        // y + bound/2
        int textY = centerY + (rect.height() / 2);
        canvas.drawText(text,textX,textY,mPaint);
    }

    /**
     * 设置进度
     * @param progress
     */
    public void setProgress(int progress){
        if (progress < 0){
            mProgress = 0;
        }else if (progress >= 0 && progress <= 100){
            mProgress = progress;
        }else {
            mProgress = progress % 100;
        }
        //发出重绘的命令，会出发onDraw执行
        invalidate();
    }
    
    /**
     * 初始化属性
     */
    private void init(Context context,AttributeSet attrs){
        mContext = context;

        //获取xml属性
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        //默认为0.3透明灰色
        int defaultColor = 0x40888888;
        mBottomColor = array.getColor(R.styleable.ProgressView_bottomColor,defaultColor);
        //默认蓝色
        mTopColor = array.getColor(R.styleable.ProgressView_topColor,Color.BLUE);
        //默认 5dp
        mRingWidth = (int)array.getDimension(R.styleable.ProgressView_ringWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5,getResources().getDisplayMetrics()));
        //字体颜色
        mTextColor = array.getColor(R.styleable.ProgressView_textColor,Color.BLUE);
        //字体大小 18sp
        mTextSize = (int)array.getDimension(R.styleable.ProgressView_textSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,24,getResources().getDisplayMetrics()));

        //回收TypeArray
        array.recycle();

        mProgress = 0;

        mPaint = new Paint();
        //设置抗锯齿
        mPaint.setAntiAlias(true);
    }

}
