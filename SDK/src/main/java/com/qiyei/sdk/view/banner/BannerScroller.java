package com.qiyei.sdk.view.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/6/25.
 * Version: 1.0
 * Description:
 */
public class BannerScroller extends Scroller {

    /**
     * 改变ViewPager切换的速率 - 动画持续的时间
     */
    private int mScrollerDuration = 900;

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollerDuration);
    }

    /**
     * @return {@link #mScrollerDuration}
     */
    public int getScrollerDuration() {
        return mScrollerDuration;
    }

    /**
     * @param scrollerDuration the {@link #mScrollerDuration} to set
     */
    public void setScrollerDuration(int scrollerDuration) {
        mScrollerDuration = scrollerDuration;
    }
}
