package com.qiyei.baselibrary.view.xrecycler;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/25.
 * Version: 1.0
 * Description: RecyclerViewDe下拉或者上拉监听器
 */
public interface XRecyclerListener {
    /**
     * 下拉刷新
     */
    void onRefresh();

    /**
     * 上拉加载更多
     */
    void onLoadMore();
}
