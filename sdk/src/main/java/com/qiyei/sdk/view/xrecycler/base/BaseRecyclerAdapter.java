package com.qiyei.sdk.view.xrecycler.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiyei.sdk.log.LogManager;
import com.qiyei.sdk.view.xrecycler.IMultiTypeLayout;

import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/19.
 * Version: 1.0
 * Description: 所有RecyclerView的Adapter的基类
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>{
    /**
     * 调试用TAG
     */
    protected static final String TAG = BaseRecyclerAdapter.class.getSimpleName();
    /**
     * context
     */
    protected Context mContext;
    /**
     * 数据集合
     */
    protected List<T> mDatas;
    /**
     * item的布局文件
     */
    protected int mLayoutId;
    /**
     * 多种item布局支持
     */
    protected IMultiTypeLayout<T> mTypeLayout;

    /**
     * item点击事件
     */
    protected OnItemClickListener mClickListener;
    /**
     * 长按事件
     */
    protected OnItemLongClickListener<T> mLongClickListener;

//    public BaseRecyclerAdapter(){
//
//    }

    public BaseRecyclerAdapter(Context context, List<T> datas, int layoutId) {
        mContext = context;
        LogManager.d(TAG,"datas :" + datas);
        mDatas = datas;
        mLayoutId = layoutId;
    }

    public BaseRecyclerAdapter(Context context, List<T> datas, IMultiTypeLayout typeLayout) {
        mContext = context;
        mDatas = datas;
        mTypeLayout = typeLayout;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //多布局支持时，利用viewType来成为布局文件
        if (mTypeLayout != null){
            mLayoutId = viewType;
        }

        View view = LayoutInflater.from(mContext).inflate(mLayoutId,parent,false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (mClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.click(v,mDatas.get(position));
                }
            });
        }
        if (mLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mClickListener.click(v,mDatas.get(position));
                    return true;
                }
            });
        }
        convert(holder,mDatas.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        //多种布局时，返回布局文件ID为布局类型
        if (mTypeLayout != null){
            return mTypeLayout.getLayoutId(mDatas.get(position),position);
        }
        return super.getItemViewType(position);
    }

    /**
     * @param clickListener the {@link #mClickListener} to set
     */
    public void setOnItemClickListener(OnItemClickListener<T> clickListener) {
        mClickListener = clickListener;
    }

    /**
     * @param longClickListener the {@link #mLongClickListener} to set
     */
    public void setOnLongClickListener(OnItemLongClickListener<T> longClickListener) {
        mLongClickListener = longClickListener;
    }

    /**
     * 设置数据
     * @param datas
     */
    public void setDatas(List<T> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }

    /**
     * 数据绑定操作
     * @param holder
     * @param item
     * @param position
     */
    public abstract void convert(BaseViewHolder holder, T item, int position);

}
