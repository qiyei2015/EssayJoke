package com.qiyei.sdk.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.qiyei.sdk.R;


/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2016/5/11.
 * Version: 1.0
 * Description: 圆形View
 */
public class CircleView extends View {

    private Paint mPaint = null;
    private int mColor;

    public CircleView(Context context){
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet set){
        super(context,set);
        TypedArray typedArray = context.obtainStyledAttributes(set, R.styleable.CircleView);
        mColor = typedArray.getColor(R.styleable.CircleView_circle_color,Color.RED);
        typedArray.recycle();
        init();
    }

    public CircleView(Context context,AttributeSet set,int defStyle){
        super(context,set,defStyle);

        TypedArray typedArray = context.obtainStyledAttributes(set, R.styleable.CircleView);
        mColor = typedArray.getColor(R.styleable.CircleView_circle_color,Color.RED);
        typedArray.recycle();
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mColor);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //这里的200是随便指定的，正常情况下需要另加考虑
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(200,200);
        } else if (widthSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(200,heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,200);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //填充属性
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        //获取该View的宽和高
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        int radius = Math.min(width,height) / 2;

        canvas.drawCircle(paddingLeft + width / 2,paddingTop + height / 2,radius,mPaint);

    }
}
