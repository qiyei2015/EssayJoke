package com.qiyei.baselibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.qiyei.baselibrary.R;


/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/14.
 * Version: 1.0
 * Description: 字体会逐步变色的TextView，其思想就是两个颜色字体分别画
 */
public class ColorTrackTextView extends TextView {

    /**
     * 字体原本颜色的画笔
     */
    private Paint mOriginalPaint;
    /**
     * 字体要改变成的颜色
     */
    private Paint mChangePaint;
    /**
     * 当前进度
     */
    private float mCurrentProgress = 0f;
    /**
     * 实现两种朝向 - 当前的朝向  从左到右还是从右到左
     */
    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    /**
     * 方向枚举
     */
    public enum Direction{
        LEFT_TO_RIGHT,  //左到右
        RIGHT_TO_LEFT   //右到左
    }

    public ColorTrackTextView(Context context) {
        this(context,null);
    }

    public ColorTrackTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorTrackTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        int originalColor = array.getColor(R.styleable.ColorTrackTextView_originalColor,getTextColors().getDefaultColor());
        int changeColor = array.getColor(R.styleable.ColorTrackTextView_changeColor,getTextColors().getDefaultColor());
        mOriginalPaint = getPaintByColor(originalColor);
        mChangePaint = getPaintByColor(changeColor);
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取宽度
        int width = getWidth();
        //获取当前进度
        int current = (int)(mCurrentProgress * width);

        String text = getText().toString();
        if (TextUtils.isEmpty(text)){
            return;
        }
        //画原始的字体
        drawOriginalText(text,canvas,current);
        //画改变颜色的字体
        drawChangeText(text,canvas,current);
    }

    /**
     * 画原始字体部分 如果是从左到右的话，字体组成：变色部分|原始部分
     * @param text
     * @param canvas
     * @param current
     */
    private void drawOriginalText(String text,Canvas canvas, int current) {
        //画未变色的部分
        if (mDirection == Direction.LEFT_TO_RIGHT){
            drawText(text,canvas,mOriginalPaint,current,getWidth());
        }else {
            drawText(text,canvas,mOriginalPaint,0,getWidth() - current);
        }
    }

    /**
     * 画变色部分
     * @param text
     * @param canvas
     * @param current
     */
    private void drawChangeText(String text,Canvas canvas, int current) {
        if (mDirection == Direction.LEFT_TO_RIGHT){
            drawText(text,canvas,mChangePaint,0,current);
        }else {
            drawText(text,canvas,mChangePaint,getWidth() - current,getWidth());
        }
    }

    /**
     * 绘制文字
     * @param text
     * @param canvas
     * @param paint
     * @param start
     * @param end
     */
    private void drawText(String text, Canvas canvas, Paint paint, int start, int end) {
        //保存画布
        canvas.save();
        //只绘制截取部分.这部分包含文字
        canvas.clipRect(new Rect(start,0,end,getHeight()));
        //获取字体的bounds
        Rect bounds = new Rect();
        paint.getTextBounds(text,0,text.length(),bounds);

        // x  就是代表绘制的开始部分  不考虑左右padding不相等的情况下 = 控件宽度的一半 - 字体宽度的一半
        int x = (getWidth() - bounds.width()) / 2;
        // y  代表的是基线 baseLine
        int dy = bounds.height() / 2 - bounds.bottom;
        // 计算基线的位置
        int baseLine = (getHeight() + bounds.height()) / 2 - dy;

        canvas.drawText(text,x,baseLine,paint);

        canvas.restore();
    }

    /**
     * 通过color获取Paint
     * @param color
     * @return
     */
    private Paint getPaintByColor(int color){
        Paint paint = new Paint();
        // 抗锯齿
        paint.setAntiAlias(true);
        // 设置颜色
        paint.setColor(color);
        // 防抖动
        paint.setDither(true);
        // 设置字体大小
        paint.setTextSize(getTextSize());
        return paint;
    }

    /**
     * @return {@link #}
     */
    public int getOriginalColor() {
        return mOriginalPaint.getColor();
    }

    /**
     * @param originalColor the {@link #} to set
     */
    public void setOriginalColor(int originalColor) {
        mOriginalPaint.setColor(originalColor);
    }

    /**
     * @return {@link #}
     */
    public int getChangeColor() {
        return mChangePaint.getColor();
    }

    /**
     * @param changeColor the {@link #} to set
     */
    public void setChangeColor(int changeColor) {
        mChangePaint.setColor(changeColor);
    }

    /**
     * @return {@link #mCurrentProgress}
     */
    public float getCurrentProgress() {
        return mCurrentProgress;
    }

    /**
     * @param currentProgress the {@link #mCurrentProgress} to set
     */
    public void setCurrentProgress(float currentProgress) {
        mCurrentProgress = currentProgress;
        invalidate();   //触发重绘
    }

    /**
     * @return {@link #mDirection}
     */
    public Direction getDirection() {
        return mDirection;
    }

    /**
     * @param direction the {@link #mDirection} to set
     */
    public void setDirection(Direction direction) {
        mDirection = direction;
    }

}
