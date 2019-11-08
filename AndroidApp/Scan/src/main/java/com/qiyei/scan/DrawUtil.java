package com.qiyei.scan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

/**
 * @author Created by qiyei2015 on 2018/7/29.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class DrawUtil {

    /**
     * 绘制文字
     * @param context
     * @param text
     * @param fontSize
     * @param color
     * @param canvas
     * @param x
     * @param y
     */
    public static void drawText(Context context, String text, int fontSize,int color,Canvas canvas,int x,int y){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //绘制文字
        paint.setColor(color);
        paint.setTextSize(DisplayUtil.dip2px(context,fontSize));
        paint.setTypeface(Typeface.create("System",Typeface.NORMAL));
        //测量text
        Rect textBound = new Rect();
        paint.getTextBounds(text,0,text.length(),textBound);
        int textWidth = textBound.right - textBound.left;
        int textHeight = textBound.bottom - textBound.top;
        canvas.drawText(text,x - textWidth/2,y - textHeight/2,paint);
    }

    /**
     * 绘制文字
     * @param context
     * @param resId
     * @param fontSize
     * @param color
     * @param canvas
     * @param x 文字中心坐标x
     * @param y 文字中心坐标y
     */
    public static void drawText(Context context, int resId,int fontSize,int color,Canvas canvas,int x,int y){
        drawText(context,context.getResources().getString(resId),fontSize,color,canvas,x,y);
    }
}
