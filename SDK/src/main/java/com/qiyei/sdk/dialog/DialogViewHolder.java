package com.qiyei.sdk.dialog;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiyei.sdk.log.LogManager;

import java.lang.ref.WeakReference;

/**
 * @author Created by qiyei2015 on 2017/5/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: Dialog辅助类
 */
public class DialogViewHolder {

    private static final String TAG = DialogViewHolder.class.getSimpleName();

    /**
     * 所引用的dialog
     */
    private BaseDialog mDialog;

    /**
     * 内容view
     */
    private View mContentView;

    /**
     * contentView中的view集合,使用弱引用，防止内存泄漏
     */
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHolder(BaseDialog dialog){
        mViews = new SparseArray<>();
        mDialog = dialog;
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
        LogManager.i(TAG,"setText tv : " + tv + ",text:" + text);
        if (tv != null){
            tv.setText(text);
            LogManager.i(TAG,"setText tv.getText --> " + tv.getText().toString());
        }
    }

    /**
     * 设置ImageView的图片
     * @param viewId
     * @param bitmap
     */
    public void setImage(int viewId, Bitmap bitmap){
        View view = getView(viewId);
        LogManager.i(TAG,"setImage view : " + view + ",bitmap:" + bitmap);

        if (view != null && view instanceof ImageView){
            ((ImageView)view).setImageBitmap(bitmap);
        }
        //如果drawable不为null，应该让view显示出来
        if (bitmap != null){
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置Drawable
     * @param viewId
     * @param drawable
     */
    public void setDrawable(int viewId, Drawable drawable){
        View view = getView(viewId);
        LogManager.i(TAG,"setDrawable view : " + view + ",resId:" + drawable);
        if (view == null){
            return;
        }
        //如果drawable不为null，应该让view显示出来
        if (drawable != null){
            view.setVisibility(View.VISIBLE);
        }
        //如果是ImageView就设置图片，否则就设置View背景
        if (view instanceof ImageView){
            ((ImageView)view).setImageDrawable(drawable);
        }else {
            view.setBackground(drawable);
        }
    }


    /**
     * 给某个view设置点击事件
     * @param viewId
     * @param listener
     */
    public void setOnClickListener(int viewId, final DialogListener listener){
        View view = getView(viewId);
        LogManager.i(TAG,"setOnItemClickListener view : " + view + ",listener:" + listener);
        if (view != null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v);
                    mDialog.dismiss();
                }
            });
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
