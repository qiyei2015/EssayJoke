package com.qiyei.baselibrary.view.xrecyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/18.
 * Version: 1.0
 * Description: RecyclerView的item的分割线
 */
public class CategoryItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = CategoryItemDecoration.class.getSimpleName();
    /**
     * 分割线的Drawable
     */
    private Drawable mDivder;

    private Rect out;

    public CategoryItemDecoration(Drawable divder) {
        mDivder = divder;

        out = new Rect();

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int count = parent.getChildCount();

        //计算要绘制的区域
        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft();
        rect.right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < count ; i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            rect.top = child.getBottom() + params.bottomMargin;
            rect.bottom = rect.top + mDivder.getIntrinsicHeight();
            //Log.d(TAG,"rect.bottom: " + rect.bottom);
            mDivder.setBounds(rect);
            mDivder.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //在每个item的bottom上算上item分割线


        out.top = outRect.top;
        out.left = outRect.left;
        out.right = outRect.right;
        out.bottom += mDivder.getIntrinsicHeight();
        outRect = out;
        //Log.d(TAG,"outRect.bottom:" + outRect.bottom);
        super.getItemOffsets(outRect,view,parent,state);
    }
}
