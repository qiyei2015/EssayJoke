package com.qiyei.baselibrary.view.xrecycler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/21.
 * Version: 1.0
 * Description:
 */
public interface IViewCreator {
    /**
     * 获取下拉刷新的View
     * @param context
     * @param parent
     */
    View getView(Context context, ViewGroup parent);

    /**
     * 正在下拉或者正在上拉
     * @param currentHeight
     * @param refreshHeight
     * @param status
     */
    void onPull(int currentHeight, int refreshHeight, XStatus status);

    /**
     * 正在刷新或者正在加载
     */
    void onRunning();

    /**
     * 停止刷新，或者停止加载
     */
    void onStopRunning();

}
