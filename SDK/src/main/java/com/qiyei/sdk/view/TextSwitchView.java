package com.qiyei.sdk.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;


import com.qiyei.sdk.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2016/11/27.
 * Version: 1.0
 * Description: 可上下滚动文字的View
 */
public class TextSwitchView extends TextSwitcher implements ViewSwitcher.ViewFactory {
    private final static String TAG = "TextSwitchView";
    private Context mContext;
    private Timer mTimer;           //定时器,用于定时执行动画，使TextView滚动播放
    private int mIndex = -1;        //索引下标
    private List<String> mResource; //字符串资源
    private float mTextSize;          //字体大小
    private int mTextColor;         //字体颜色

    private final static int SCROLL = 1;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SCROLL:
                    updateText();
                    break;
                default:
                    break;

            }
        }
    };

    public TextSwitchView(Context context){
        this(context,null);
    }

    public TextSwitchView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context,attrs);
    }

    /**
     * 初始化
     * @param context
     * @param attrs
     */
    private void init(Context context,AttributeSet attrs){
        mContext = context;
        if (mTimer == null){
            mTimer = new Timer();
        }
        TypedArray array = mContext.obtainStyledAttributes(attrs,R.styleable.TextSwitchView);
        mTextSize = array.getDimensionPixelSize(R.styleable.TextSwitchView_textSize,16);//默认16sp
        mTextColor = array.getColor(R.styleable.TextSwitchView_textColor, Color.BLACK);//默认黑色
        array.recycle();
        //获取自定义属性应该在setFactory()之前
        setFactory(this);
        setInAnimation(AnimationUtils.loadAnimation(context, R.anim.in_animation));
        setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.out_animation));
    }

    @Override
    public View makeView() {
        TextView textView = new TextView(mContext);
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        Log.d(TAG,"mTextSize:" + (int)(mTextSize / fontScale + 0.5f));
        textView.setTextSize((int)(mTextSize / fontScale + 0.5f));
        textView.setTextColor(mTextColor);
        return textView;
    }

    /**
     * 设置资源
     * @param res
     */
    public void setResource(List<String> res){
        mResource = res;
    }

    /**
     * 设置文字停留时间
     * @param time
     */
    public void setTextStayTime(long time){
        if (mTimer == null){
            mTimer = new Timer();
        }else {
            mTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(SCROLL);
                }
            },0,time);
        }
    }

    /**
     * 更新Text
     * @return
     */
    private void updateText(){
        mIndex++;
        if (mIndex > mResource.size() - 1){
            mIndex = 0;
        }
        setText(mResource.get(mIndex));
    }

    /**
     * 这里防止内存泄漏
     */
    @Override
    protected void onDetachedFromWindow() {
        mTimer.cancel();//取消定时任务
        mHandler.removeMessages(SCROLL);//取消改Message的队列循环
        super.onDetachedFromWindow();
    }
}
