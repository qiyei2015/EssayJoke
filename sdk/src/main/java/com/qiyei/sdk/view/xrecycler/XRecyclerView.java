package com.qiyei.sdk.view.xrecycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import com.qiyei.sdk.log.LogUtil;
import android.view.MotionEvent;
import android.view.View;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/23.
 * Version: 1.0
 * Description:
 */
public class XRecyclerView extends WrapRecyclerView{
    /**
     * 调试标志
     */
    private static final String TAG = XRecyclerView.class.getSimpleName();
    /**
     * 下拉刷新
     */
    private XRecyclerHelper mRefresh;
    /**
     * 上拉加载更多
     */
    private XRecyclerHelper mLoadMore;
    /**
     * 是否下拉刷新
     */
    private boolean isPullRefresh;
    /**
     * 是否上拉加载更多
     */
    private boolean isPullLoad;
    /**
     * 下拉刷新和上拉加载更多的监听器
     */
    private XRecyclerListener mListener;
    /**
     * 第一个可见的item
     */
    private int mFirstVisibile = -1;
    /**
     * 最后一个可见的item
     */
    private int mLastVisibile = -1;
    /**
     * 布局管理器
     */
    private LayoutManager mLayoutManager;

    public XRecyclerView(Context context) {
        this(context,null);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public XRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && isPullRefresh){

            int refreshHeight = mRefresh.view.getMeasuredHeight();
            mRefresh.viewHeight = refreshHeight;
            LogUtil.d(TAG,"onLayout,mRefresh.viewHeight:" + refreshHeight);
            if (refreshHeight > 0){
                mRefresh.setViewTopMargin(-refreshHeight);   //隐藏掉RefreshView
            }
        }
    }

    /**
     * 记录手指按下的位置 ,之所以写在dispatchTouchEvent那是因为如果我们处理了条目点击事件，
     * 那么就不会进入onTouchEvent里面，所以只能在这里获取
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mRefresh.downY = (int) ev.getRawY();
                mLoadMore.downY = (int)ev.getRawY();

                break;
            case MotionEvent.ACTION_UP:         //手指离开时，如果是下拉或者上拉，就恢复refreshview
                if (mRefresh.drag){
                    mRefresh.restoreViewTopMargin(mListener);
                }
                if (mLoadMore.drag){
                    mLoadMore.restoreViewBottomMargin(mListener);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean isScrollTop = false;
        mLayoutManager = getLayoutManager();
        if (mLayoutManager instanceof LinearLayoutManager){
            mFirstVisibile = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
            if (mFirstVisibile == 0 || mFirstVisibile == 1){
                isScrollTop = true;
            }else {
                isScrollTop = false;
            }
        }else {
            isScrollTop = false;
        }

        switch (e.getAction()){
            case MotionEvent.ACTION_MOVE:

                LogUtil.d(TAG,"onTouchEvent,mRefresh.status:" + mRefresh.status);
                //如果在最顶部或者底部才处理，否则不需要处理
                if (mRefresh.status == XStatus.RUNNING || mLoadMore.status == XStatus.RUNNING){
                    return super.onTouchEvent(e);
                }

//                //如果在下拉就滚动到第一个位置,要不然的话，看不到
                if (mRefresh.drag && isScrollTop){
                    scrollToPosition(0);
                }

                if (mLoadMore != null && isPullLoad){
                    mLoadMore.viewHeight = mLoadMore.view.getMeasuredHeight();
                }

                //如果在上拉就滚动到最后一个位置，要不然看不到
                if (mLoadMore.drag && !canScrollDown()){
                    scrollToPosition(getAdapter().getItemCount() - 1);
                }

                int refreshY = (int)((e.getRawY() - mRefresh.downY) * mRefresh.dragValue);
                int loadMoreY = (int)((e.getRawY() - mLoadMore.downY) * mLoadMore.dragValue);

                //手指下拉
                if (refreshY > 0 && isScrollTop && isPullRefresh){
                    refreshY = refreshY - mRefresh.viewHeight;
                    LogUtil.d(TAG,"onTouchEvent,refreshY:" + refreshY);
                    mRefresh.setViewTopMargin(refreshY);
                    mRefresh.updateViewStatus(refreshY);
                    mRefresh.drag = true;
                    return false;
                }
                //手指上拉
                if (loadMoreY < 0 && !canScrollDown() && isPullLoad){
                    LogUtil.d(TAG,"onTouchEvent,loadMoreY:" + -loadMoreY);
                    mLoadMore.setViewBottomMargin(-loadMoreY);
                    mLoadMore.updateViewStatus(-loadMoreY);
                    mLoadMore.drag = true;
                    return false;
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    /**
     * 初始化
     */
    private void init(){
        mRefresh = new XRecyclerHelper(XRecyclerHelper.REFRESH);
        mLoadMore = new XRecyclerHelper(XRecyclerHelper.LOAD_MORE);
    }

    /**
     * 添加下拉刷新的Creator
     * @param creator
     */
    public void addRefreshViewCreator(IViewCreator creator){
        isPullRefresh = true;
        mRefresh.addViewCreator(creator);
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null && mRefresh.creator != null){
            View header = mRefresh.creator.getView(getContext(),this);
            if (header != null){
                addRefreshView(header);
                mRefresh.view = header;
            }
        }
    }

    /**
     * 添加上拉加载更多的的Creator
     * @param creator
     */
    public void addLoadMoreViewCreator(IViewCreator creator){
        isPullLoad = true;
        mLoadMore.addViewCreator(creator);
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter != null && mLoadMore.creator != null){
            View foot = mLoadMore.creator.getView(getContext(),this);
            if (foot != null){
                addLoadMoreView(foot);
                mLoadMore.view = foot;
            }
        }
    }

    /**
     * @param pullRefresh the {@link #isPullRefresh} to set
     */
    public void setPullRefresh(boolean pullRefresh) {
        isPullRefresh = pullRefresh;
        if (isPullRefresh){
            addRefreshViewCreator(new CommonRefreshView());
        }
    }

    /**
     * @param pullLoad the {@link #isPullLoad} to set
     */
    public void setPullLoad(boolean pullLoad) {
        isPullLoad = pullLoad;
        if (isPullLoad){
            addLoadMoreViewCreator(new CommonRefreshView());
        }
    }

    /**
     * 停止刷新
     */
    public void stopRefresh(){
        mRefresh.status = XStatus.NORMAL;
        mRefresh.restoreViewTopMargin(mListener);
        if (mRefresh.creator != null){
            mRefresh.creator.onStopRunning();
        }
    }

    /**
     * 停止加载
     */
    public void stopLoadMore(){
        mLoadMore.status = XStatus.NORMAL;
        mLoadMore.restoreViewBottomMargin(mListener);
        if (mLoadMore.creator != null){
            mLoadMore.creator.onStopRunning();
        }
    }

    /**
     * @param  listener the {@link #mListener} to set
     */
    public void setXRecyclerListener(XRecyclerListener listener) {
        mListener = listener;
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     * 判断是不是滚动到了最顶部，这个是从SwipeRefreshLayout里面copy过来的源代码
     */
    public boolean canScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            return ViewCompat.canScrollVertically(this, -1) || this.getScrollY() > 0;
        } else {
            return ViewCompat.canScrollVertically(this, -1);
        }
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     * 判断是不是滚动到了最顶部，这个是从SwipeRefreshLayout里面copy过来的源代码
     */
    public boolean canScrollDown() {
        return ViewCompat.canScrollVertically(this, 1);
    }

}
