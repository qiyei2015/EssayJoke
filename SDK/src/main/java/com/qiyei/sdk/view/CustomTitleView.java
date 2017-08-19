package com.qiyei.sdk.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;


import com.qiyei.sdk.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by daner on 2016/7/26.
 * 1273482124@qq.com
 */
/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2016/7/26.
 * Version: 1.0
 * Description: 自定义View
 */
public class CustomTitleView extends View implements View.OnClickListener{
    private static final String TAG = "CustomTitleView";

    //文本
    private String mTitleText;
    //文本颜色
    private int mTitleColor;
    //文本字体
    private int mTitleTextSize;

    //绘制时控制文本的范围
    private Rect mRect;
    //画笔
    private Paint mPaint;

    //监听器
    private OnClickListener mListener;

    public CustomTitleView(Context context){
        this(context,null);
    }

    public CustomTitleView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    /**
     * 获取自定义的样式属性，一般来说是必须要有的
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomTitleView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        init(context,attrs);

        //设置监听器
        this.setOnClickListener(this);
    }

    private void init(Context context,AttributeSet attrs){
        /**
         * 获取自定义的样式属性(从xml文件)
         */
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CustomTitleView);

        mTitleText = typedArray.getString(R.styleable.CustomTitleView_titleText);

        //默认为黑色
        mTitleColor = typedArray.getColor(R.styleable.CustomTitleView_titleColor, Color.BLACK);

        //默认设置16sp,TypeValue可以把sp转化为px
        mTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomTitleView_titleTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));

        Log.d(TAG,"mTitleText:" + mTitleText);
        Log.d(TAG,"mTitleColor:" + mTitleColor);
        Log.d(TAG,"mTitleSize:" + mTitleTextSize);

        typedArray.recycle();

        //初始化绘笔
        mPaint = new Paint();
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTitleTextSize);
        mPaint.setColor(mTitleColor);

        //获得绘制文本的宽和高
        mRect = new Rect();
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mRect);
    }


    public String randomText() {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 6)
        {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set)
        {
            sb.append("" + i);
        }
        return sb.toString();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;

        //得到宽和高的测量模式和规格
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        mPaint.setTextSize(mTitleTextSize);
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mRect);

        Log.d(TAG,"mRect.width()" + mRect.width());

        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        } else {
            //加上左右两边的padding区域和字符的宽度
            width = getPaddingLeft() + mRect.width() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        } else {
            //加上上下两边的padding区域和字符的高度
            height = getPaddingTop() + mRect.height() + getPaddingBottom();
        }

        //设置测量尺寸
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画一个矩形框,长宽为测量后的宽高
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        mPaint.setColor(mTitleColor);
        //画在正中间
        canvas.drawText(mTitleText,getMeasuredWidth()/2 - mRect.width()/2
                ,getMeasuredHeight()/2 + mRect.height()/2,mPaint);
    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v);
        //重新布局
        requestLayout();
        //该View重绘
        postInvalidate();
    }

    public void setTitleText(String text){
        mTitleText = text;
    }

    public void setOnClickListener(OnClickListener listener){
        mListener = listener;
    }

    public interface OnClickListener{
        void onClick(View v);
    }

}
