package com.qiyei.sdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2016/5/10.
 * Version: 1.0
 * Description: 扩展的ListView
 */
public class ListViewEx extends ListView {
    private static final String TAG = "ListViewEx";

    //分别记录上次移动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    private HorizontalScrollViewEx2 mHorizontalScrollViewEx2;

    public ListViewEx(Context context){
        super(context);
    }

    public ListViewEx(Context context, AttributeSet att){
        super(context,att);
    }

    public void setmHorizontalScrollViewEx2(HorizontalScrollViewEx2 horizontalScrollViewEx2){
        mHorizontalScrollViewEx2 = horizontalScrollViewEx2;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastX;
                Log.d(TAG,"dx:" + deltaX + " dy:" + deltaY);
                if (Math.abs(deltaX) > Math.abs(deltaY)){
                    mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(ev);
    }
}
