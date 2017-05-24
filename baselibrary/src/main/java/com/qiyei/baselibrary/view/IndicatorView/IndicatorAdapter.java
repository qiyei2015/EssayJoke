package com.qiyei.baselibrary.view.IndicatorView;

import android.view.View;
import android.view.ViewGroup;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/15.
 * Version: 1.0
 * Description: 指示器的适配器
 */
public abstract class IndicatorAdapter<T extends View> {
    /**
     * item总个数
     * @return
     */
    public abstract int getCount();

    /**
     * 获取指定位置的view
     * @param position
     * @param parent
     * @return
     */
    public abstract T getView(int position, ViewGroup parent);

    /**
     * 高亮indicator
     * @param view
     */
    public abstract void highLightIndicator(T view);

    /**
     * 重置indicator
     * @param view
     */
    public abstract void restoreIndicator(T view);

    /**
     * 获取底部指示器的view
     * @return
     */
    public abstract View getBottomTrackView();

}
