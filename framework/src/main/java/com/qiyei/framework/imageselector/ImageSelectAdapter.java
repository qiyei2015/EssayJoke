package com.qiyei.framework.imageselector;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.qiyei.framework.R;
import com.qiyei.sdk.image.ImageManager;
import com.qiyei.sdk.log.LogUtil;
import com.qiyei.sdk.util.FileUtil;
import com.qiyei.sdk.util.ToastUtil;
import com.qiyei.sdk.view.xrecycler.IMultiTypeLayout;
import com.qiyei.sdk.view.xrecycler.base.BaseRecyclerAdapter;
import com.qiyei.sdk.view.xrecycler.base.BaseViewHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Email: 1273482124@qq.com
 * Created by qiyei2015 on 2017/5/8.
 * Version: 1.0
 * Description:
 */
public class ImageSelectAdapter extends BaseRecyclerAdapter<ImageEntity> {

    private Context mContext;
    /**
     * 模式
     */
    private int mMode;
    /**
     * 所选的Images
     */
    private List<String> mSelectImages;
    /**
     * 最大可选张数
     */
    private int mMaxCount;
    /**
     * 图片选择时的更新监听
     */
    private UpdateSelectListener mListener;

    public ImageSelectAdapter(Context context, List<String> selectImages,List<ImageEntity> images,int maxCount, int mode) {
        super(context,images, R.layout.media_chooser_item);
        this.mContext = context;
        mSelectImages = selectImages;
        this.mMaxCount = maxCount;
        this.mMode = mode;
    }

    public ImageSelectAdapter(Context context, List<ImageEntity> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    public ImageSelectAdapter(Context context, List<ImageEntity> datas, IMultiTypeLayout typeLayout) {
        super(context, datas, typeLayout);
    }

    @Override
    public void convert(BaseViewHolder holder, final ImageEntity item, int position) {
        if (item.name == null) {
            holder.setVisibility(View.INVISIBLE, R.id.image, R.id.mask, R.id.media_selected_indicator);
            holder.setVisibility(View.VISIBLE, R.id.camera_tv);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCamera();
                }
            });
        } else {
            holder.setVisibility(View.VISIBLE, R.id.image, R.id.media_selected_indicator);
            holder.setVisibility(View.INVISIBLE, R.id.camera_tv);
            // 显示图片
            ImageView imageView = holder.getView(R.id.image);

//            Glide.with(mContext)
//                    .load(item.path)
//                    .centerCrop()
//                    .into(imageView);

            ImageManager.getInstance().loadImage(imageView,item.path);

            ImageView selectedIndicatorIv = holder.getView(R.id.media_selected_indicator);
            selectedIndicatorIv.setSelected(mSelectImages.contains(item.path));

            // 设置选中效果
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectImages.contains(item.path)) {
                        mSelectImages.remove(item.path);
                    } else {
                        // 判断是否到达最大
                        if (mMaxCount == mSelectImages.size()) {
                            ToastUtil.showLongToast(mContext.getString(R.string.max_select) +
                                    mMaxCount + mContext.getString(R.string.num_photo));
                            return;
                        }
                        mSelectImages.add(item.path);
                    }
                    if (mListener != null) {
                        mListener.imageSelect();
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * 打开相机拍照
     */
    private void openCamera() {
        try {
            File tmpFile = FileUtil.createTmpFile(mContext);
            if (mListener != null) {
                mListener.openCamera(tmpFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtil.showLongToast(mContext.getString(R.string.open_camera_fail));
        }
    }

    /**
     * 设置数据
     */
    public void setShowCamera(boolean showCamera) {
        if (showCamera) {
            ImageEntity imageEntity = new ImageEntity();
            mDatas.add(0,imageEntity);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置listener
     * @param listener
     */
    public void setOnUpdateSelectListener(UpdateSelectListener listener) {
        this.mListener = listener;
    }

    /**
     * 图片选择更新的监听
     */
    public interface UpdateSelectListener {
        /**
         * 图片选择回调
         */
        void imageSelect();

        /**
         * 打开相机回调
         * @param file
         */
        void openCamera(File file);
    }

}
