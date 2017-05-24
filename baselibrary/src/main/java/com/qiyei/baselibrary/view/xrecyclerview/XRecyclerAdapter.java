package com.qiyei.baselibrary.view.xrecyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/20.
 * Version: 1.0
 * Description: 利用装饰器设计模式来构造添加头部和底部的RecyclerAdapter
 */
public abstract class XRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {

    /**
     * 头部的View
     */
    private SparseArray<View> mHeaderViews;
    /**
     * 底部View
     */
    private SparseArray<View> mFooterViews;
    /**
     * 头部的View索引
     */
    private int BASE_TYPE_HEADER = 100000;
    /**
     * 底部的View索引
     */
    private int BASE_TYPE_FOOTER = 200000;
    /**
     * 不包含头部和底部的adapter
     */
    private BaseRecyclerAdapter mAdapter;


    public XRecyclerAdapter(Context context, List<T> datas, int layoutId) {
        super(context, datas, layoutId);
        init();
    }

    public XRecyclerAdapter(Context context, List<T> datas, IMultiTypeLayout typeLayout) {
        super(context, datas, typeLayout);
        init();
    }

    public XRecyclerAdapter(BaseRecyclerAdapter adapter) {
        mAdapter = adapter;
        init();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeaderView(viewType)) {
            return onCreateHeaderViewHolder(parent, viewType);
        }

        if (isFooterView(viewType)){
            return onCreateFooterViewHolder(parent, viewType);
        }

        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (isHeaderPosition(position) || isFooterPosition(position)){
            return;
        }
        super.onBindViewHolder(holder, position - mHeaderViews.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)){
            return mHeaderViews.keyAt(position);
        }

        if (isFooterPosition(position)){
            return mFooterViews.keyAt(position - mHeaderViews.size() - super.getItemCount());
        }

        return super.getItemViewType(position - mHeaderViews.size());
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + mHeaderViews.size() + mFooterViews.size();
    }

    /**
     * 添加HeaderView
     * @param view
     */
    public void addHeaderView(View view){
        int position = mHeaderViews.indexOfValue(view);
        if (position < 0){
            mHeaderViews.put(BASE_TYPE_HEADER++,view);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加FooterView
     * @param view
     */
    public void addFooterView(View view){
        int position = mFooterViews.indexOfValue(view);
        if (position < 0){
            mFooterViews.put(BASE_TYPE_FOOTER++,view);
        }
        notifyDataSetChanged();
    }

    /**
     * 移除HeaderView
     * @param view
     */
    public void removeHeaderView(View view){
        int position = mHeaderViews.indexOfValue(view);
        if (position < 0){
            return;
        }
        mHeaderViews.removeAt(position);
        notifyDataSetChanged();
    }

    /**
     * 移除FooterView
     * @param view
     */
    public void removeFooterView(View view){
        int position = mFooterViews.indexOfValue(view);
        if (position < 0){
            return;
        }
        mFooterViews.removeAt(position);
        notifyDataSetChanged();
    }

    /**
     * 解决GridLayoutManager添加头部和底部不占用一行的问题
      * @param recycler
     */
    public void adjustSpanSize(RecyclerView recycler){
        if (recycler.getLayoutManager() instanceof GridLayoutManager){
            final GridLayoutManager layoutManager = (GridLayoutManager) recycler.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter =
                            isHeaderPosition(position) || isFooterPosition(position);
                    return isHeaderOrFooter ? layoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 初始化
     */
    private void init(){
        mHeaderViews = new SparseArray<>();
        mFooterViews = new SparseArray<>();
    }

    /**
     * 判断是否是HeaderView
     * @param viewType
     * @return
     */
    private boolean isHeaderView(int viewType){
        int index = mHeaderViews.indexOfKey(viewType);
        return index >= 0;
    }

    /**
     * 判断是否是HeaderView
     * @param viewType
     * @return
     */
    private boolean isFooterView(int viewType){
        int index = mFooterViews.indexOfKey(viewType);
        return index >= 0;
    }

    /**
     * 创建HeaderViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    private BaseViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType){
        View view = mHeaderViews.get(viewType);
        return new BaseViewHolder(view);
    }

    /**
     * 创建FooterViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    private BaseViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType){
        View view = mFooterViews.get(viewType);
        return new BaseViewHolder(view);
    }

    /**
     * 判断此position是否是Header
     * @param position
     * @return
     */
    private boolean isHeaderPosition(int position){
        return position < mHeaderViews.size();
    }

    /**
     * 判断此处是否是Footer
     * @param position
     * @return
     */
    private boolean isFooterPosition(int position){
        return position >= mHeaderViews.size() + super.getItemCount();
    }

}
