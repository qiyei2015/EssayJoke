package com.qiyei.sdk.view.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;


import java.lang.reflect.Field;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/25.
 * Version: 1.0
 * Description:
 */
public class BannerViewPager extends ViewPager {
    /**
     * 调试用的标志
     */
    public final static String TAG = "Banner";
    /**
     * Hanlder,用于处理轮播
     */
    private LoopHandler mHandler;
    /**
     * 处理消息的msg
     */
    private final static int MSG_LOOP = 0x111;
    /**
     * 循环时间
     */
    private final static int LOOP_TIME = 2000;
    /**
     * 切换时间
     */
    private int switchTime = LOOP_TIME;
    /**
     * BannerViewPager的Adapter
     */
    private BannerPageAdapter mAdapter;
    /**
     * 动画切换时的Scroller，通过它可以改变轮播的速度
     */
    private BannerScroller mScroller;

    /**
     * 轮播的Handler
     */
    private class LoopHandler extends Handler{

        public LoopHandler(){
            super();
        }

        public LoopHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_LOOP:
                    int index = getCurrentItem() + 1;
                    setCurrentItem(index % mAdapter.getCount(),true);
                    startLoop();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    public BannerViewPager(Context context) {
        super(context,null);
        init(context,null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    /**
     * 初始化
     * @param context
     * @param attr
     */
    private void init(Context context, AttributeSet attr){
        mHandler = new LoopHandler(Looper.getMainLooper());
        mScroller = new BannerScroller(context);

        //反射，设置到ViewPager中的mScroller
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(this,mScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Adapter
     * @param adapter
     */
    public void setAdapter(BannerPageAdapter adapter) {
        mAdapter = adapter;
        super.setAdapter(adapter);
    }

    /**
     * @return {@link #mAdapter}
     */
    @Override
    public BannerPageAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        if (mHandler == null){
//            mHandler = new LoopHandler(Looper.getMainLooper());
//        }
    }

    /**
     * 销毁Hanlder，防止内存泄漏
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //mHandler.removeMessages(MSG_LOOP);
        //mHandler = null;
    }

    /**
     * 开启轮播
     */
    public void startLoop(){
        Message msg = Message.obtain();
        msg.what = MSG_LOOP;
        mHandler.sendMessageDelayed(msg,switchTime);
    }

    /**
     * 停止轮播
     */
    public void stopLoop(){
        mHandler.removeMessages(MSG_LOOP);
        mHandler = null;
    }

    /**
     * 设置页面切换时间
     * @param time
     */
    public void setSwitchTime(int time){
        switchTime = time;
    }

    /**
     * 设置轮播动画的持续时间
     * @param time 单位为ms
     */
    public void setDuration(int time){
        mScroller.setScrollerDuration(time);
    }

    /**
     * 设置viewpager中的item的点击事件
     */
    public void setItemClickListener(BannerItemClickListener listener) {
        mAdapter.setItemClickListener(listener);
    }


}
