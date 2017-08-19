package com.qiyei.sdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hanxw on 2016/8/2.
 */
/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2016/8/2.
 * Version: 1.0
 * Description: 四个布局？？？？？
 */
public class FourLayout extends ViewGroup {
    private Context mContext;


    public FourLayout(Context context){
        this(context,null);
    }

    public FourLayout(Context context , AttributeSet attrs){
        this(context,attrs,0);
    }

    public FourLayout(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        mContext = context;

    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //返回MarginLayoutParams
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //测量各个子元素的宽高
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        /**
         * 下面是根据子元素测量后，测量自己的宽高
         */
        int width = 0;
        int height = 0;
        MarginLayoutParams param = null;

        //左 右的高度
        int leftHeight = 0;
        int rightHeight = 0;

        //上 下 的宽度
        int topWidth = 0;
        int bottomWidth = 0;

        for (int i = 0;i < getChildCount();i++){
            //获取子View
            View child = getChildAt(i);
            param = (MarginLayoutParams) child.getLayoutParams();

            //最上面两个
            if (i == 0 || i == 1){
                topWidth += param.leftMargin + child.getMeasuredWidth() + param.rightMargin;
            }

            //最左边两个
            if (i == 0 || i == 2){
                leftHeight += param.topMargin + child.getMeasuredHeight() + param.bottomMargin;
            }

            //最下面两个
            if (i == 2 || i == 3){
                bottomWidth += param.leftMargin + child.getMeasuredWidth() + param.rightMargin;
            }

            //最上面两个
            if (i == 1 || i == 3){
                rightHeight += param.topMargin + child.getMeasuredHeight() + param.bottomMargin;
            }
        }

        //获取两者的最大值
        width = Math.max(topWidth,bottomWidth);
        height = Math.max(leftHeight,rightHeight);

        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childWidth = 0;
        int childHeight = 0;
        MarginLayoutParams param = null;

        /**
         * 遍历所有子View,根据其宽高对其进行margin布局
         */
        for (int i = 0;i < getChildCount();i++){
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            param = (MarginLayoutParams) child.getLayoutParams();

            int left = 0;
            int right = 0;
            int top = 0;
            int bootom = 0;

            switch (i){
                case 0:
                    left = param.leftMargin;
                    top = param.topMargin;
                    break;
                case 1:
                    left = getMeasuredWidth() - childWidth - param.rightMargin;
                    top = param.topMargin;
                    break;
                case 2:
                    left = param.leftMargin;
                    top = getMeasuredHeight() - childHeight - param.bottomMargin;
                    break;
                case 3:
                    left = getMeasuredWidth() - childWidth - param.rightMargin;
                    top = getMeasuredHeight() - childHeight - param.bottomMargin;
                    break;
                default:
                    break;
            }

            right = left + childWidth;
            bootom = top + childHeight;

            //调用child的layout完成子view的layout
            child.layout(left,top,right,bootom);
        }

    }



}
