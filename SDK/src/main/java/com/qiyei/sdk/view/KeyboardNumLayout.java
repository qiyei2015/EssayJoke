package com.qiyei.sdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2016/8/3.
 * Version: 1.0
 * Description: 实现自定义的数字键盘，方便项目使用。
 *              使用说明如下：
 *              引入custom_num_keyboard.xml文件，然后在Activity中调用setItemSelectedListener设置监听器
 *              按下键后会返回对应的值，数字返回对应的数字，其他分别返回:"clear","next","ok","","."
 */
public class KeyboardNumLayout extends LinearLayout{
    private static final String TAG = "KeyboardNumLayout";
    private Context mContext;
    //点击的item监听器
    private OnItemSelectedListener mListener;

    private int mWidth = 0;
    private int mHeight = 0;
    private int delta = 5;

    public KeyboardNumLayout(Context context){
        this(context,null);
    }

    public KeyboardNumLayout(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public KeyboardNumLayout(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        mContext = context;
    }

    /**
     * ViewGroup已经初始化完成了，可以获取宽和高了。
     * @param hasWindowFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG,"X:" + event.getX() + " Y:" + event.getY());
                if (mListener != null){
                    mListener.onItemSelected(checkTouchNum((int)event.getX(),(int)event.getY()));
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setItemSelectedListener(OnItemSelectedListener listener){
        mListener = listener;
    }


    public interface OnItemSelectedListener{
        void onItemSelected(String num);
    }

    /**
     * 根据按下的x,y判断按下的是哪个区域
     * @param x
     * @param y
     * @return
     */
    private String checkTouchNum(int x,int y){
        Log.d(TAG,"width:" + mWidth + " height:" + mHeight);
        if (x < mWidth/4 - delta){
            if (y < mHeight/4 - delta){
                return 1 + "";
            } else if ((y < 2 * mHeight/4 - delta)&&( y > mHeight/4 + delta)){
                return 4 + "";
            }
            else if ((y < 3 * mHeight/4 - delta)&&( y > 2 * mHeight/4 + delta)){
                return 7 + "";
            }
            else if ((y <mHeight)&&( y > 3 * mHeight/4 + delta)){
                return ".";
            }
        }else if((x < 2 * mWidth/4 - delta)&&( x > mWidth/4 + delta)){
            if (y < mHeight/4 - delta){
                return 2 + "";
            } else if ((y < 2 * mHeight/4 - delta)&&( y > mHeight/4 + delta)){
                return 5 + "";
            }
            else if ((y < 3 * mHeight/4- delta )&&( y > 2 * mHeight/4+ delta )){
                return 8 + "";
            }
            else if ((y <mHeight)&&( y > 3 * mHeight/4 + delta)){
                return 0 + "";
            }
        }else if((x < 3 * mWidth/4 - delta)&&( x > 2 * mWidth/4 + delta)){
            if (y < mHeight/4 - delta){
                return 3 + "";
            } else if ((y < 2 * mHeight/4 - delta)&&( y > mHeight/4 + delta)){
                return 6 + "";
            }
            else if ((y < 3 * mHeight/4 - delta)&&( y > 2 * mHeight/4 + delta)){
                return 9 + "";
            }
            else if ((y <mHeight)&&( y > 3 * mHeight/4 + delta)){
                return "";
            }
        }else if((x <mWidth)&&( x > 3*mWidth/4 + delta)){
            if (y < mHeight/4 - delta){
                return "clear";
            } else if ((y < 2 * mHeight/4 - delta)&&( y > mHeight/4 + delta)){
                return "next";
            }
            else if ((y < mHeight)&&( y > 2 * mHeight/4 + delta)){
                return "ok";
            }
        }
        return "error";
    }

}
