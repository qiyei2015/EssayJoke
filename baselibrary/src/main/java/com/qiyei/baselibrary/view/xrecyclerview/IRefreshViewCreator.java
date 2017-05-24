package com.qiyei.baselibrary.view.xrecyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/21.
 * Version: 1.0
 * Description:
 */
public interface IRefreshViewCreator {
    /**
     * 获取下拉刷新的View
     * @param context
     * @param parent
     */
    View getRefreshView(Context context, ViewGroup parent);

    /**
     * 正在下拉
     * @param currentHeight
     * @param refreshHeight
     * @param status
     */
    void onPull(int currentHeight, int refreshHeight, XRecyclerView.RefrshStatus status);

    /**
     * 正在刷新
     */
    void onRefreshing();

    /**
     * 停止刷新
     */
    void onStopRefreshing();

}
