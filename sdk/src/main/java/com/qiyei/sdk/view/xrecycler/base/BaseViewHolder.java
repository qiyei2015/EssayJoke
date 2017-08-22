package com.qiyei.sdk.view.xrecycler.base;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qiyei.sdk.image.ImageManager;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/19.
 * Version: 1.0
 * Description: Recycler的ViewHolder,方法使用链式调用
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    /**
     * 调试用TAG
     */
    protected static final String TAG = BaseViewHolder.class.getSimpleName();

    /**
     * 缓存View
     */
    protected SparseArray<View> mViews;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    /**
     * 获取对应的View
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        //没有就从itemView 中查找
        if (view == null){
            view = itemView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        if (view == null){
            throw new NullPointerException("getView: " + viewId + "is null , please check !");
        }
        return (T) view;
    }

    /**
     * 设置文本内容
     * @param viewId
     * @param text
     */
    public BaseViewHolder setText(int viewId, CharSequence text){
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    /**
     * 设置文本内容
     * @param viewId
     * @param resId
     */
    public BaseViewHolder setText(int viewId, int resId){
        TextView textView = getView(viewId);
        textView.setText(resId);
        return this;
    }

    /**
     * 设置文本内容
     * @param viewId
     * @param color
     */
    public BaseViewHolder setTextColor(int viewId, int color){
        TextView textView = getView(viewId);
        textView.setTextColor(color);
        return this;
    }

    /**
     * 设置文本内容
     * @param viewId
     * @param size
     */
    public BaseViewHolder setTextSize(int viewId, int size){
        TextView textView = getView(viewId);
        textView.setTextSize(size);
        return this;
    }

    /**
     * 设置View的Visibility
     * @param visibility
     * @param viewId
     * @return
     */
    public BaseViewHolder setVisibility(int visibility, int viewId) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    /**
     * 设置View的Visibility
     * @param visibility
     * @param args
     * @return
     */
    public BaseViewHolder setVisibility(int visibility,int...args) {
        for (int i = 0;i < args.length ;i++){
            getView(args[i]).setVisibility(visibility);
        }
        return this;
    }

    /**
     * 设置ImageView的资源
     * @param viewId
     * @param resourceId
     * @return
     */
    public BaseViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    /**
     * 设置ImageView的图片
     * @param viewId
     * @param bitmap
     * @return
     */
    public BaseViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置image的url
     * @param viewId
     * @param url
     */
    public void setImageUrl(int viewId,String url){
        ImageView imageView = getView(viewId);
        //加载图片
        ImageManager.getInstance().loadImage(imageView,url);
    }

    /**
     * 设置item中某个view的点击事件
     * @param viewId
     * @param listener
     */
    public void setOnClickListener(int viewId,View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
    }

    /**
     * 设置item中某个view的长点击事件
     * @param viewId
     * @param listener
     */
    public void setOnLongClickListener(int viewId,View.OnLongClickListener listener) {
        getView(viewId).setOnLongClickListener(listener);
    }

    /**
     * 设置View可见
     * @param id
     */
    public void setViewVisible(int id){
        getView(id).setVisibility(View.VISIBLE);
    }

    /**
     * 设置View Gone
     * @param id
     */
    public void setViewGone(int id){
        getView(id).setVisibility(View.GONE);
    }

//    /**
//     * 设置图片加载url,这里稍微处理得复杂一些，因为考虑加载图片的第三方可能不太一样
//     * 也可以直接写死
//     * @param viewId
//     * @param imageLoader
//     * @return
//     */
//    public BaseViewHolder setImageByUrl(int viewId, HolderImageLoader imageLoader) {
//        ImageView imageView = getView(viewId);
//        if (imageLoader == null) {
//            throw new NullPointerException("imageLoader is null!");
//        }
//        imageLoader.displayImage(imageView.getContext(), imageView, imageLoader.getImagePath());
//        return this;
//    }
//
//    /**
//     * 图片加载，这里稍微处理得复杂一些，因为考虑加载图片的第三方可能不太一样
//     * 也可以不写这个类
//     */
//    public static abstract class HolderImageLoader {
//        private String mImagePath;
//
//        public HolderImageLoader(String imagePath) {
//            this.mImagePath = imagePath;
//        }
//
//        public String getImagePath() {
//            return mImagePath;
//        }
//
//        public abstract void displayImage(Context context, ImageView imageView, String imagePath);
//    }

}
