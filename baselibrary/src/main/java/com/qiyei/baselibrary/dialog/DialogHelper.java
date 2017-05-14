package com.qiyei.baselibrary.dialog;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/13.
 * Version: 1.0
 * Description: Dialog辅助类
 */
public class DialogHelper {

    /**
     * 内容view
     */
    private View mContentView;

    /**
     * contentView中的view集合,使用弱引用，防止内存泄漏
     */
    private SparseArray<WeakReference<View>> mViews;

    public DialogHelper(){
        mViews = new SparseArray<>();
    }

    /**
     * 根据id获取对应的view
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        WeakReference<View> viewWeakReference = mViews.get(viewId);

        View view = null;

        if (viewWeakReference != null){
            view = viewWeakReference.get();
        }

        if (view == null){
            view = mContentView.findViewById(viewId);
            if (view != null){
                mViews.put(viewId,new WeakReference<View>(view));
            }
        }
        return (T) view;
    }

    /**
     * 设置文本内容
     * @param viewId
     * @param text
     */
    public void setText(int viewId,CharSequence text){
        TextView tv = getView(viewId);
        if (tv != null){
            tv.setText(text);
        }
    }

    /**
     * 给某个view设置点击事件
     * @param viewId
     * @param listener
     */
    public void setOnClickListener(int viewId,View.OnClickListener listener){
        View view = getView(viewId);
        if (view != null){
            view.setOnClickListener(listener);
        }
    }

    /**
     * @return {@link #mContentView}
     */
    public View getContentView() {
        return mContentView;
    }

    /**
     * @param contentView the {@link #mContentView} to set
     */
    public void setContentView(View contentView) {
        mContentView = contentView;
    }

}
