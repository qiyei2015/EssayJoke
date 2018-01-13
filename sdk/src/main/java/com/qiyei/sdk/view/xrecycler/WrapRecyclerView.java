package com.qiyei.sdk.view.xrecycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter;
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/20.
 * Version: 1.0
 * Description: 自定义扩展的Recycler，支持添加HeaderView,FooterView，上拉加载更多，下拉刷新，以及侧滑删除等
 */
public class WrapRecyclerView extends RecyclerView {
    /**
     * 调试用的标志
     */
    private final static String TAG = WrapRecyclerView.class.getSimpleName();
    /**
     * 未有头与底的adapter
     */
    private BaseRecyclerAdapter mAdapter;
    /**
     * 封装了头部与底部的Adapter
     */
    private XRecyclerAdapter mWarpAdapter;

    /**
     * 数据为空的显示页
     */
    private View mEmptyView;
    /**
     * 加载页
     */
    private View mLoadingView;

    /**
     * 定义数据观察者
     */
    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkAdapter(mAdapter);
            if (mWarpAdapter != mAdapter){
                mWarpAdapter.notifyDataSetChanged();
            }
            dataChanged();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            checkAdapter(mAdapter);
            if (mWarpAdapter != mAdapter){
                mWarpAdapter.notifyItemMoved(fromPosition,toPosition);
            }
            dataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            checkAdapter(mAdapter);
            if (mWarpAdapter != mAdapter){
                mWarpAdapter.notifyItemRangeChanged(positionStart,itemCount);
            }
            dataChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            checkAdapter(mAdapter);
            if (mWarpAdapter != mAdapter){
                mWarpAdapter.notifyItemRangeChanged(positionStart,itemCount,payload);
            }
            dataChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkAdapter(mAdapter);
            if (mWarpAdapter != mAdapter){
                mWarpAdapter.notifyItemRangeInserted(positionStart,itemCount);
            }
            dataChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkAdapter(mAdapter);
            if (mWarpAdapter != mAdapter){
                mWarpAdapter.notifyItemRangeRemoved(positionStart,itemCount);
            }
            dataChanged();
        }
    };

    public WrapRecyclerView(Context context) {
        super(context);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param adapter the {@link #mAdapter} to set
     */
    public void setAdapter(Adapter adapter) {
        if (mAdapter != null){
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
            mAdapter = null;
        }

        mAdapter = (BaseRecyclerAdapter) adapter;

        if (adapter instanceof XRecyclerAdapter){
            mWarpAdapter = (XRecyclerAdapter) adapter;
        }else {
            mWarpAdapter = new XRecyclerAdapter(this.getContext(),null,0){
                @Override
                public void convert(BaseViewHolder holder, Object item, int position) {

                }
            };
        }

        super.setAdapter(mWarpAdapter);

        mAdapter.registerAdapterDataObserver(mDataObserver);
        //调整GridLayoutManager的添加头部和底部不占一行的问题
        mWarpAdapter.adjustSpanSize(this);
    }

    /**
     * 添加HeaderView
     * @param view
     */
    public void addHeaderView(View view){
        checkAdapter(mWarpAdapter);
        mWarpAdapter.addHeaderView(view);
    }

    /**
     * 添加FooterView
     * @param view
     */
    public void addFooterView(View view){
        checkAdapter(mWarpAdapter);
        mWarpAdapter.addFooterView(view);
    }

    /**
     * 移除HeaderView
     * @param view
     */
    public void removeHeaderView(View view){
        checkAdapter(mWarpAdapter);
        mWarpAdapter.removeHeaderView(view);
    }

    /**
     * 添加HeaderView到指定位置
     * @param view
     */
    public void addRefreshView(View view){
        checkAdapter(mWarpAdapter);
        mWarpAdapter.addRefreshView(view);
    }

    /**
     * 添加FooterView
     * @param view
     */
    public void addLoadMoreView(View view){
        checkAdapter(mWarpAdapter);
        mWarpAdapter.addLoadMoreView(view);
    }

    /**
     * 移除FooterView
     * @param view
     */
    public void removeFooterView(View view){
        checkAdapter(mWarpAdapter);
        mWarpAdapter.removeFooterView(view);
    }

    /**
     * 添加空的view
     * @param view
     */
    public void setEmptyView(View view){
        this.mEmptyView = view;
    }

    /**
     * 设置加载页
     * @param view
     */
    public void setLoadingView(View view){
        this.mLoadingView = view;
    }

    /**
     * 监测Adapter
     * @param adapter
     */
    private void checkAdapter(Adapter adapter){
        if (adapter == null){
            throw new NullPointerException("adapter is null,please setAdapter() first");
        }
    }

    /**
     * 数据改变时需要判断是否为空
     */
    private void dataChanged() {
        if (mEmptyView == null){
            return;
        }
        if (mAdapter.getItemCount() == 0){
            mEmptyView.setVisibility(VISIBLE);
        }else {
            mEmptyView.setVisibility(GONE);
        }
    }
}
